<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" encoding="utf-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
	<xsl:template match="/">
	
		<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
				<title>Clientèle</title>
			</head>
			<body>
				<table width="1000" border="1" cellspacing="0" cellpadding="0" align="center" >
					<tr>
						<th scope="col">Nom</th>
						<th scope="col">Id</th>
						<th scope="col">Age</th>
						<th scope="col">Comptes</th>
					</tr>
					<xsl:for-each select="gestionnaire/client">
						<tr align="center">
							<td><xsl:value-of select="nom"/></td>
							<td><xsl:value-of select="id"/></td>
							<td><xsl:value-of select="age"/></td>
							<td>
								<table width="100%">
								<xsl:if test="(comptes)">
									<tr>
										<th scope="col">No</th>
										<th scope="col">Type</th>
										<th scope="col">Solde</th>
									</tr>
									<xsl:for-each select="comptes/compte">
										<tr align="center">
											<td><xsl:value-of select="@no"/></td>
											<td><xsl:value-of select="type"/></td>
											<td><xsl:value-of select="solde"/></td>
										</tr>
									</xsl:for-each>
								</xsl:if>
								</table>
							</td>
						</tr>
					</xsl:for-each>
				</table>				
			</body>
		</html>
		
	</xsl:template>
</xsl:stylesheet>