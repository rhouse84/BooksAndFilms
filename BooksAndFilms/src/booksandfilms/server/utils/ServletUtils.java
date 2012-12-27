package booksandfilms.server.utils;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

	public static String getRelativeUrl(HttpServletRequest request) {

		String baseUrl = null;

		if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
			baseUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		else
			baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();

		StringBuffer buf = request.getRequestURL();

		if (request.getQueryString() != null) {
			buf.append("?");
			buf.append(request.getQueryString());
		}

		return buf.substring(baseUrl.length());
	}

	public static String getBaseUrl(HttpServletRequest request) {
		if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
			return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		else
			return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	}

	/**
	* Returns the file specified by <tt>path</tt> as returned by <tt>ServletContext.getRealPath()</tt>.
	*/
	public static File getRealFile(HttpServletRequest request, String path) {
		return new File(request.getSession().getServletContext().getRealPath(path));
	}
}
