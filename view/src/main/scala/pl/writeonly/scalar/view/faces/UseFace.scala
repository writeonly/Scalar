package pl.writeonly.scalar.view.faces

import com.typesafe.scalalogging.slf4j.StrictLogging
import javax.annotation.Resource
import org.springframework.stereotype.Controller
import pl.writeonly.babel.beans._
import pl.writeonly.babel.daos.DaoCsv
import pl.writeonly.babel.dtos.LernuQuery
import pl.writeonly.scalar.view.providers.DefaultTableProvider
import javax.annotation.PostConstruct
import pl.writeonly.xscalawt.viewers.TableViewerBuilder
import org.eclipse.jface.viewers.TableViewer
import pl.writeonly.scalar.view.cards.UseCard
import pl.writeonly.scalar.view.Face
import pl.writeonly.scalar.view.Facade

@org.springframework.stereotype.Controller
class UseFace extends Face {
  @Resource var dialogFace: Facade = _
  @Resource var sideService: SideBean = _
  @Resource var csvService: CsvBean = _
  @Resource var daoCsv: DaoCsv = _
  @Resource var lernuQueryTP: DefaultTableProvider[LernuQuery] = _

  @PostConstruct
  def init() {
    lernuQueryTP.array = Array[LernuQuery](new LernuQuery("UseFace", "UseFace", "UseFace"))
  }

  def apply() = UseCard(this)

  def viewSideItem = sideService.findName.toArray
  def open() = {
    logger.debug("open");
    val fileName = dialogFace.open.open();
    val readed = csvService.readFile(fileName)
    logger.debug("fileName " + fileName);
    lernuQueryTP.array = daoCsv.read(classOf[LernuQuery], fileName).toArray[LernuQuery]
  }

  def addLernuQueries(viewer: TableViewer) = {
    val list = open[LernuQuery](classOf[LernuQuery])
    list.foreach((query: LernuQuery) => {
      viewer.add(query)
    })
  }

  /**
   * wczytuje plik cvs, konwertuje na klasy i wyświetla
   */
  def open[T](clazz: Class[T]) = {
    val fileName = dialogFace.open.open();
    daoCsv.read(clazz, fileName)
  }
  def use() = {
    logger.debug("use")
    //sideService.

  }
}