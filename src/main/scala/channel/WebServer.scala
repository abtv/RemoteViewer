package channel

import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}

class WebServer(val port: Int) {
  private val server  = new org.eclipse.jetty.server.Server(port)
  private val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
  context.setContextPath("/")
  server.setHandler(context)
  context.addServlet(new ServletHolder(new ImagesServlet()),  "/*")

  server.start
}