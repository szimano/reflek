package org.szimano.reflek.browser

class BrowserController {
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
