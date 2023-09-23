import java.net.URL
import scala.collection.concurrent.TrieMap
import scala.concurrent.blocking

/**
 * Copy from play framework.
 */
object VersionedResourceFinder {
  // Sames goes for the minified paths cache.
  private lazy val minifiedPathsCache = TrieMap[String, String]()

  private def resource(str: String): Option[URL] = Option(
    getClass.getClassLoader.getResource(str)
  )

  private def minifiedPath(path: String): String = {
    minifiedPathsCache.getOrElse(
      path, {
        def minifiedPathFor(delim: Char): Option[String] = {
          val ext = path.reverse.takeWhile(_ != '.').reverse
          val noextPath = path.dropRight(ext.length + 1)
          val minPath = noextPath + delim + "min." + ext
          Option(getClass.getResource(minPath)).map(_ => minPath)
        }

        val maybeMinifiedPath =
          minifiedPathFor('.').orElse(minifiedPathFor('-')).getOrElse(path)
        minifiedPathsCache.put(path, maybeMinifiedPath)
        maybeMinifiedPath
      }
    )
  }

  private lazy val digestCache = TrieMap[String, Option[String]]()

  lazy val digestAlgorithm: String = "md5"

  private def digest(path: String): Option[String] = {
    digestCache.getOrElse(
      path, {
        val maybeDigestUrl: Option[URL] =
          resource(path + "." + digestAlgorithm)
        val maybeDigest: Option[String] = maybeDigestUrl.map { url =>
          val source = scala.io.Source.fromURL(url)
          try source.getLines.mkString.trim
          finally source.close()
        }
        if (maybeDigest.isDefined) digestCache.put(path, maybeDigest)
        maybeDigest
      }
    )
  }

  def findAssetPath(base: String, path: String): String = blocking {
    val minPath = minifiedPath(path)
    digest(minPath)
      .fold(minPath) { dgst =>
        val lastSep = minPath.lastIndexOf("/")
        minPath.take(lastSep + 1) + dgst + "-" + minPath.drop(lastSep + 1)
      }
      .drop(base.length + 1)
  }
}
