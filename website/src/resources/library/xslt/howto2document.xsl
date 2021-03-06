<?xml version="1.0"?>

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">

 <xsl:import href="copyover.xsl"/>

  <xsl:template match="all">
   <document>
    <xsl:copy-of select="howto/header"/>
     <body>
        <xsl:apply-templates select="howto"/>
     	<xsl:apply-templates select="revisions"/>
	</body>
  
   </document>
  </xsl:template>
  
  <xsl:template match="howto">
    <xsl:if test="normalize-space(header/abstract)!=''">
      <xsl:apply-templates select="header/abstract"/>
    </xsl:if>
     <xsl:apply-templates select="*[not(name()='header')]"/>
  </xsl:template>
  
  <xsl:template match="howto/header/abstract">
    <section>
     <title>Overview</title>
      <xsl:apply-templates/>
    </section>
  </xsl:template>
  
  <xsl:template match="purpose | prerequisites | audience | steps | extension  | faqs | tips | references | feedback ">
    <section>
      <xsl:choose>
        <xsl:when test="normalize-space(@title)!=''">
          <title><xsl:value-of select="@title"/></title>
        </xsl:when>
        <xsl:otherwise>
          <title><xsl:value-of select="name()"/></title>
        </xsl:otherwise>
      </xsl:choose>
     <xsl:apply-templates/>
    </section>
  </xsl:template>
  
  <xsl:template match="faq">
    <section>
     <title>
      <xsl:value-of select="question"/>
     </title>
      <xsl:apply-templates select="answer" />
    </section>
  </xsl:template>
  
   <xsl:template match="answer">
      <xsl:copy-of select="."/>
    </xsl:template>
    
   <xsl:template match="question">
    </xsl:template>
  
  <xsl:template match="revisions">
    <section>
     <title>Revisions</title>
    <p>Find a problem with this document? Consider contacting the author or submitting your own revision. For instructions, read the How To Submit a Revision.</p>
      <ul>
       <xsl:apply-templates select="revision"/>
      </ul>
    </section>
  </xsl:template>
  
  <xsl:template match="revision">
  <xsl:variable name="href"><xsl:value-of select="concat(substring-before(@name,'.xml'),'.html')" /></xsl:variable>
   <li>Revision, <a href="{ $href}"><xsl:value-of select="@date"/></a></li>
  </xsl:template>
  
</xsl:stylesheet>
