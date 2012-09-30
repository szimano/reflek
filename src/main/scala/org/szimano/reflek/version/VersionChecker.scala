package org.szimano.reflek.version

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import java.util.Properties
import javax.swing.{JOptionPane, JFrame}
import swing.MainFrame
import java.io.InputStreamReader


class VersionChecker {

  val VERSION = "version"
  val VERSION_MESSAGE = "version.message"

  def checkVersion(frame: MainFrame) = {
    val httpClient = new DefaultHttpClient()

    val response = httpClient.execute(new HttpGet("http://szimano.org/reflek/version-info.properties"))

    val serverProperties = new Properties()
    serverProperties.load(new InputStreamReader(response.getEntity.getContent, "UTF-8"))

    val localProperties = new Properties()
    localProperties.load(getClass.getResourceAsStream("/version.properties"))

    if (!localProperties.getProperty(VERSION).equals(serverProperties.getProperty(VERSION))) {

      val options: Array[AnyRef] = Array("OK", "Idź do strony programu")

      if (JOptionPane.showOptionDialog(frame.peer, serverProperties.getProperty(VERSION_MESSAGE), "Nowa Wersja",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options(0)) == 1) {

        openBrowser("http://szimano.org/reflek")
      }
    }
  }

  def openBrowser(url: String) = {

    val desktop = java.awt.Desktop.getDesktop();

    if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
      // just ignore
    }
    else {
      try {
        val uri = new java.net.URI(url);
        desktop.browse( uri );
      }
    }
  }
}
