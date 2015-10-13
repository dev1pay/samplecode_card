<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" action="Card" id="form">
		<table width="100%" border="0" cellpadding="3" cellspacing="3">
			<tr>
				<td colspan="2" align="center">
					<h2>Nạp thẻ điện thoại</h2>
				</td>

			</tr>
			<tr>
				<td align="right" width="40%">Nhà Mạng :</td>
				<td><select id="lstTelco" name="lstTelco">
						<option value="viettel">Viettel</option>
						<option value="mobifone">MobiFone</option>
						<option value="vinaphone">Vinaphone</option>
						<option value="gate">Gate</option>
						<option value="vcoin">Vcoin</option>
						<option value="zing">Zing</option>
						<option value="bit">Bit</option>
						<option value="vnmobile">vietnamobile</option>
				</select></td>
			</tr>
			<tr>

				<td align="right">Số Seri :</td>
				<td><input type="text" id="txtSeri" name="txtSeri" /></td>
			</tr>
			<tr>
				<td align="right">Mã số :</td>
				<td><input type="text" id="txtCode" name="txtCode" /></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td><input type="submit" value="Nạp thẻ" /></td>
			</tr>
		</table>
	</form>
</body>
</html>