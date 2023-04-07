import scala.util.Random
import scala.collection.parallel.CollectionConverters._

object PageRank {
    /**
     * @param pages A map of page.id to page for some number of WebPage objects
     * @return      A map of page.id to a weight of 1.0 for those same WebPage objects
     */
    def equal(pages: Map[String, WebPage]): Map[String, Double] = {
        pages.map((x, y) => (x, 1.0))
    }

    /**
     * @param pages A map of page.id to page for some number of WebPage objects
     * @return A map of page.id to a weight that is a simple count of the number of pages linking to that page
     */
    def indegree(pages: Map[String, WebPage]): Map[String, Double] = {
        pages.map((id,page) => (id, (pages.count((x,y) => y.links.contains(id))).toDouble))
    }

    def pagerank(pages: Map[String, WebPage]): Map[String, Double] = {
        val r = Random()
        val ks = pages.keys.toList
        (1 until 10000).flatMap(i => {
            val i = r.nextInt(pages.size)
            val pageId = ks(i)
            (1 until 100).foldRight(List(pageId -> 1)) {
                (i, l) => {
                    val id = l(l.length - 1)._1
                    val f = r.nextFloat()
                    val w =.85 / (if pages(id).links.length > 0 then pages(id).links.length else 1)
                    if (f < w * pages(id).links.length) {
                        l :+ (pages(id).links((f / w).toInt) -> 1)
                    } else {
                        l :+ (ks(r.nextInt(pages.size)) -> 1)
                    }
                }
            }
        }).groupMapReduce(_._1)(_._2)(_ + _).map((k, v) => (k, (v + 1.0) / (pages.size + 10000)))
    }
}