package vn.onepay.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Card
 */
@WebServlet("/Card")
public class Card extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String USER_AGENT = "Mozilla/5.0";
	String json = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String accessKey = ""; // access key do 1pay cung cap. Thay bang access key san pham cua ban
		String secretKey = ""; // secret key do 1pay cung cap. Thay bang secret key san pham cua ban
		String type = request.getParameter("lstTelco");
		String pin = request.getParameter("txtCode");
		String serial = request.getParameter("txtSeri");
		String transRef = randomString();
		try {
			sendPost(accessKey, type, pin, serial, transRef,secretKey);
			request.setAttribute("json", json);
			request.getRequestDispatcher("WEB-INF/jsp/Result.jsp").forward(
					request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String randomString() {
		final String RAND = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			builder.append(RAND.charAt(random.nextInt(RAND.length())));
		}
		System.out.println(builder.toString());
		return builder.toString();
	}

	private void sendPost(String access_key, String type, String pin,
			String serial, String transRef, String key) throws Exception {
		String url = "https://api.1pay.vn/card-charging/v5/topup";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "access_key=%access_key%&pin=%pin%&serial=%serial%&transRef=%transRef%&type=%type%";
		urlParameters = urlParameters.replaceFirst("%access_key%", access_key);
		urlParameters = urlParameters.replaceFirst("%type%", type);
		urlParameters = urlParameters.replaceFirst("%pin%", pin);
		urlParameters = urlParameters.replaceFirst("%serial%", serial);
		urlParameters = urlParameters.replaceFirst("%transRef%", transRef);

		HmacSHA256 hmacSHA256 = HmacSHA256.getInstance(key);
		String signature = hmacSHA256.sign(urlParameters);
		System.out.println("Signature:" + signature);
		urlParameters = urlParameters + "&signature=" + signature;

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("URL : " + url);
		System.out.println("Parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		json = response.toString();
		System.out.println("1:" + json);
	}
}
