<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">  <xsl:template match="site">
    <html>
      <head>
        <title><xsl:value-of select="div[@class='content']/h1"/></title>
        <link rel="stylesheet" href="skin/page.css" type="text/css"/>
      </head>
      <body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
      <!-- =========================== top line with navigation path ========================== -->
<!--
    <table cellspacing="0" cellpadding="0" border="0" width="100%" summary="navigation path">
      <tr>
        <td height="20" bgcolor="#CFDCED" valign="middle" nowrap="nowrap">
          <img src="skin/images/spacer.gif" border="0" alt="" width="5" height="1" />
-->          
          <!-- breadcrumb trail (javascript-generated) -->
<!--
          <font face="Arial, Helvetica, Sans-serif" size="2">
              <script type="text/javascript" language="JavaScript" src="skin/breadcrumbs.js"></script>
          </font>
-->
          <!-- end breadcrumb trail -->
          <!-- old trail -->
          <!--<a href="@link1.href@">@link1@</a> &#62; <a href="@link2.href@">@link2@</a> &#62; <a href="@link3.href@">@link3@</a>-->
<!--
        </td>
      </tr>
      <tr>
        <td height="2" bgcolor="#4C6C8F"><img src="skin/images/spacer.gif" border="0" alt="" width="2" height="2" /></td>
      </tr>
    </table>
-->
    <!-- ================================= top bar with logo's and search box ===================================  -->
        <table cellspacing="0" cellpadding="0" border="0" width="100%" summary="header with logos">
      <tr>
        <!--
        <td bgcolor="#294563"><a href="@group-logo.href@"><img src="@group-logo.src@" alt="@group-logo.alt@" width="220" height="65" border="0"/></a></td>
        -->
        <!--
        <td bgcolor="#294563" align="center" width="100%"><a href="@project-logo.href@"><img src="@project-logo.src@" border="0" width="220" height="65" alt="@project-logo.alt@" /></a></td>
        -->
        <td bgcolor="#294563">
        </td>
        <td bgcolor="#294563" align="center" width="100%">
        <div style="font-family:mono,courier;font-weight:bold;font-size:50pt;color:#B2C4E0;text-shadow:black">TN5250J</div></td>        
        <td bgcolor="#294563" rowspan="2" valign="top">
<!--
          <form method="get" action="http://www.google.com/search" onsubmit="q.value = query.value + ' site:xml.apache.org'" target="_blank">
            <table bgcolor="#4C6C8F" cellpadding="0" cellspacing="0" border="0" summary="search">
              <tr>
                <td colspan="3"><img src="skin/images/spacer.gif" alt="" width="1" height="10" /></td>
              </tr>
              <tr>
                <td><img src="skin/images/spacer.gif" alt="" width="1" height="1" /></td>
                <td nowrap="nowrap">
                  <input type="hidden" name="q"/>
                  <input type="text" id="query" size="15"/><img src="skin/images/spacer.gif" alt="" width="5" height="1" /><input type="submit" value="Search" name="Search"/>
                  <br />
                  <font color="white" size="2" face="Arial, Helvetica, Sans-serif">
                  the xml.apache.org sites
-->                  
<!-- setting search options off for the moment -->
<!--
                    <input type="radio" name="web" value="web"/>web site&#160;&#160;<input type="radio" name="mail" value="mail"/>mail lists
-->
<!--
                  </font>
                </td>
                <td><img src="skin/images/spacer.gif" alt="" width="1" height="1" /></td>
              </tr>
              <tr>
                <td><img src="skin/images/search-left.gif" width="9" height="10" border="0" alt="" /></td>
                <td><img src="skin/images/spacer.gif" alt="" width="1" height="1" /></td>
                <td><img src="skin/images/search-right.gif" width="9" height="10" border="0" alt="" /></td>
              </tr>
            </table>
          </form>
-->        
        </td>
       <td bgcolor="#294563"><img src="skin/images/spacer.gif" alt="" width="10" height="10" /></td>
      </tr>
<!--
      <tr>
        <td colspan="2" bgcolor="#294563" valign="bottom">
            <xsl:apply-templates select="div[@class='tab']"/>
        </td>
        <td bgcolor="#294563"><img src="skin/images/spacer.gif" height="1" width="1" alt="" /></td>
      </tr>
-->
      <tr>
        <td colspan="4" bgcolor="#4C6C8F"><img src="skin/images/spacer.gif" alt="" height="10" width="1" /></td>
      </tr>
    </table>
    <!-- ======================================  Menu and Content table ====================================== -->
    <table cellspacing="0" cellpadding="0" border="0" width="100%" bgcolor="#ffffff" summary="page content">
      <tr>
        <td valign="top">
          <table cellpadding="0" cellspacing="0" border="0" summary="menu">
            <tr>
              <td valign="top" rowspan="3">
                <table cellspacing="0" cellpadding="0" border="0" summary="blue line">
                  <tr><td bgcolor="#294563"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td></tr>
                  <tr><td bgcolor="#CFDCED"><font face="Arial, Helvetica, Sans-serif" size="4" color="#4C6C8F">&#160;</font></td></tr>
                  <tr><td bgcolor="#294563"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td></tr>
                </table>
              </td>
              <td bgcolor="#294563"><img src="skin/images/spacer.gif" alt="" height="1" width="1" /></td>
              <td bgcolor="#4C6C8F" valign="bottom"><img src="skin/images/spacer.gif" alt="" height="10" width="10" /></td>
              <td bgcolor="#4C6C8F" valign="top" nowrap="nowrap">
                  <xsl:apply-templates select="div[@class='menu']"/>
              </td>
              <td bgcolor="#4C6C8F" valign="bottom"><img src="skin/images/spacer.gif" alt="" height="10" width="10" /></td>
              <td bgcolor="#294563"><img src="skin/images/spacer.gif" alt="" height="1" width="1" /></td>
            </tr>
            <tr>
              <td bgcolor="#4C6C8F" rowspan="2" colspan="2" align="left" valign="bottom"><img src="skin/images/menu-left.gif" alt="" border="0" width="10" height="10" /></td>
              <td bgcolor="#4C6C8F"><img src="skin/images/spacer.gif" alt="" border="0" width="10" height="10" /></td>
              <td bgcolor="#4C6C8F" rowspan="2" colspan="2" align="right" valign="bottom"><img src="skin/images/menu-right.gif" alt="" border="0" width="10" height="10" /></td>
            </tr>
            <tr>
              <td bgcolor="#294563" height="1"><img src="skin/images/spacer.gif" alt="" height="1" width="1" /></td>
            </tr>
          </table>
        </td>
        <td width="100%" valign="top">
          <table cellspacing="0" cellpadding="0" border="0" width="100%" summary="content">
            <tr><td bgcolor="#294563" colspan="4"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td></tr>
            <tr>
              <td bgcolor="#CFDCED" width="10" align="left"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td>
              <td bgcolor="#CFDCED" width="50%" align="left">
<!-- ========================================= Page number ===================================== -->
                <font face="Arial, Helvetica, Sans-serif" size="3" color="#4C6C8F">
                &#160;
<!--
                  <b>Page 1 of 5</b>
-->
                </font>
                  <img src="skin/images/spacer.gif" alt="" height="8" width="10" />
              </td>
              <td bgcolor="#CFDCED" width="50%" align="right">
<!--  ====================================== page navigation ===================================== -->
                <font face="Arial, Helvetica, Sans-serif" size="3" color="#4C6C8F">
                &#160;
<!--
                  <b>&#171; prev&#160;&#160;<font size="4">[3]</font>&#160;&#160;next &#187;</b>
-->
                </font>
                  <img src="skin/images/spacer.gif" alt="" height="8" width="10" />
              </td>
              <td bgcolor="#CFDCED" width="10"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td>
            </tr>
            <tr><td bgcolor="#294563" colspan="4"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td></tr>
            <tr>
              <td width="10" align="left"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td>
              <td width="100%" align="left">
                <xsl:apply-templates select="div[@class='content']"/>
              </td>
              <td width="10"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
<!-- footer -->
    <table border="0" height="20" width="100%" cellpadding="0" cellspacing="0" summary="footer">
      <tr>
        <td width="10"><img src="skin/images/spacer.gif" alt="" height="1" width="10" /></td>
      </tr>
      <tr><td bgcolor="#4C6C8F" height="1" colspan="2"><img src="skin/images/spacer.gif" alt="" width="1" height="1" /><a href="skin/images/label.gif"><img src="skin/images/spacer.gif" alt="" width="1" height="1" border="0"/></a><a href="skin/images/page.gif"><img src="skin/images/spacer.gif" alt="" width="1" height="1" border="0"/></a><a href="skin/images/chapter.gif"><img src="skin/images/spacer.gif" alt="" width="1" height="1" border="0"/></a><a href="skin/images/chapter_open.gif"><img src="skin/images/spacer.gif" alt="" width="1" height="1" border="0"/></a><a href="skin/images/current.gif"><img src="skin/images/spacer.gif" alt="" width="1" height="1" border="0"/></a></td></tr>
      <tr>
        <td align="center" class="copyright" bgcolor="#CFDCED" colspan="2">
          <font face="Arial, Helvetica, Sans-Serif" size="2">Copyright &#169; @year@ @vendor@. All Rights Reserved<script language="JavaScript" type="text/javascript"><![CDATA[<!--
              document.write(". - "+"Last Published: " + document.lastModified);
            //  -->]]></script></font>
        </td>
      </tr>
    </table>
      </body>
    </html></xsl:template>
    <xsl:template match="node()|@*" priority="-1">
      <xsl:copy>
        <xsl:apply-templates select="@*"/>
        <xsl:apply-templates/>
      </xsl:copy>
    </xsl:template>
  </xsl:stylesheet>
