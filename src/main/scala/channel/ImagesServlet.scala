package channel

import javax.servlet.annotation.WebServlet
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import application.IO

@WebServlet
class ImagesServlet() extends HttpServlet {
  protected override def doGet(req: HttpServletRequest, response: HttpServletResponse) {
    val image = IO.get()

    if (image != null){
      response.setHeader("Content-Type", "image/jpg")
      response.setHeader("Content-Length", image.length.toString)
      response.getOutputStream().write(image)
    }
  }
}