package webchannel

import javax.servlet._
import javax.servlet.http.HttpServletResponse

/**
 * Created by daynin on 26.10.14.
 */
@javax.inject.Singleton
class CorsFilter extends Filter {
  def doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
    if (response.isInstanceOf[HttpServletResponse]) {
      val alteredResponse: HttpServletResponse = (response.asInstanceOf[HttpServletResponse])
      addCorsHeader(alteredResponse)
    }
    filterChain.doFilter(request, response)
  }

  private def addCorsHeader(response: HttpServletResponse) {
    response.addHeader("Access-Control-Allow-Origin", "*")
    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD")
    response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept")
    response.addHeader("Access-Control-Max-Age", "1728000")
    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")
  }

  def destroy {
  }

  def init(filterConfig: FilterConfig) {
  }
}