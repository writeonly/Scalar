package pl.writeonly.scalar.view.faces

import org.eclipse.jface.viewers.TableViewer
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Table

import pl.writeonly.xscalawt.viewers.TableViewerBuilder
import com.typesafe.scalalogging.slf4j.StrictLogging

import javax.annotation.Resource
import pl.writeonly.babel.beans.RecordBean
import pl.writeonly.babel.beans.WordBean
import pl.writeonly.babel.entities.Record
import pl.writeonly.scalar.view.Face
import pl.writeonly.scalar.view.cards.RecordCard
import pl.writeonly.scalar.view.providers.RecordTableProvider
import pl.writeonly.scalar.view.providers.RelationTableProvider
import pl.writeonly.scala.util.SingleAbstractMethod._

@org.springframework.stereotype.Controller
class RecordFace extends Face {
  @Resource var recordService: RecordBean = _
  @Resource var tableProvider: RecordTableProvider = _

  var records: TableViewerBuilder[Record] = _
  var table: Table = _
  var viewer: TableViewer = _

  def apply() = RecordCard(this)

  def setAll {}
  def resetAll {}
  def insert = recordService.persistAll (checked())
  def find = try { addAll(recordService.find) } catch { case e: RuntimeException => logger error (e.getMessage(), e) }
  def remove {}
  def translate {}
  def addAll(recordAll: List[Record]) {
    tableProvider.add(recordAll.toArray)
    Display.getDefault.syncExec { recordAll.foreach(records.viewer.add(_)) }
  }
  def checked() = records.table.getItems.filter(_.getChecked()).map(_.getData().asInstanceOf[Record]).toList

}