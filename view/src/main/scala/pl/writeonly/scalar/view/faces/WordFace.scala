package pl.writeonly.scalar.view.faces

import org.eclipse.jface.viewers.TableViewer
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Table
import pl.writeonly.xscalawt.viewers.TableViewerBuilder
import com.typesafe.scalalogging.slf4j.StrictLogging
import javax.annotation.Resource
import pl.writeonly.babel.beans.WordBean
import pl.writeonly.babel.entities.Relation
import pl.writeonly.babel.entities.Word
import pl.writeonly.scalar.view.Face
import pl.writeonly.scalar.view.cards.WordCard
import pl.writeonly.scalar.view.providers.WordTableProvider
import pl.writeonly.scala.util.SingleAbstractMethod._
import pl.writeonly.babel.remote.JmsDestination

import pl.writeonly.scala.util.SingleAbstractMethod._

@org.springframework.stereotype.Controller
class WordFace extends Face {
  //@Resource var jmsDestination: JmsDestination = _
  @Resource var wordService: WordBean = _
  @Resource var tableProvider: WordTableProvider = _
  var builder: TableViewerBuilder[Relation] = _
  var viewer: TableViewer = _
  var table: Table = _

  def apply() = WordCard(this)

  def setAll {}
  def resetAll {}
  def persist = wordService.persistAll (checked())
  def find = try { addAll(wordService.find) } catch { case e: RuntimeException => runtime(e) }
  def remove {}
  def translate {}
  def addAll(wordAll: List[Word]) {
    tableProvider.add(wordAll.toArray)
    Display.getDefault.syncExec { wordAll.foreach(builder.viewer.add(_)) }
  }
  def checked() = builder.table.getItems.filter(_.getChecked()).map(_.getData().asInstanceOf[Word]).toList
  def add(word: Word) = {
    tableProvider.add(Array(word))
    Display.getDefault.syncExec { builder.viewer.add(word) }
  }

}