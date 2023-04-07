import scala.math.log
import scala.collection.parallel.CollectionConverters._

object PageSearch {
  /**
   * @param pages a list of RankedWebPage objects to be searched
   * @param query a list of search terms to be counted in those pages
   * @return a list of the number of times any of the terms appeared in each page in the same order as given
   */
  def count(pages: List[RankedWebPage], query: List[String]): List[Double] = {
    pages.map(
      r => query.map(w => r.text.split(" ").count(s => s.toLowerCase.contains(w.toLowerCase))).sum.toDouble
    )
  }

  /**
   * @param pages a list of RankedWebPage objects to be searched
   * @param query a list of search terms to be counted in those pages
   * @return a list of the term-frequency of the occurrences of those terms in each page in the same order given
   */
  def tf(pages: List[RankedWebPage], query: List[String]): List[Double] = {
    pages.map(
      r => query.map(
        w => r.text.split(" ").count(s => s.toLowerCase.contains(w.toLowerCase)).toDouble / r.text.length
      ).sum
    )
  }

  /**
   * @param pages a list of RankedWebPage objects to be searched
   * @param query a list of search terms to be counted in those pages
   * @return a list of the TF-IDF score for each page in the same order given
   */
  def tfidf(pages: List[RankedWebPage], query: List[String]): List[Double] = {
    pages.map(
      r => query.map(
        w => r.text.split(" ").count(
          s => s.toLowerCase.contains(w.toLowerCase)
        ).toDouble / r.text.length * math.log(
          pages.length.toDouble / (pages.count(_.text.toLowerCase.contains(w.toLowerCase)) + 1)
        )
      ).sum
    )
  }
}