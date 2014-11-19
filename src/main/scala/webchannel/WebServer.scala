package webchannel

import application.TServer
import org.eclipse.jetty.servlet.{FilterHolder, ServletContextHandler, ServletHolder}

class WebServer(port: Int) extends TServer{
  private val server  = new org.eclipse.jetty.server.Server(port)
  private val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
  context.setContextPath("/")
  server.setHandler(context)
  context.addServlet(new ServletHolder(new ImagesServlet()),  "/*")
  context.addFilter(new FilterHolder(new CorsFilter()),"/*",0)

  override def start(): Unit = server.start
}