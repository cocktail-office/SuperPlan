<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE xsl:stylesheet
[
<!ENTITY  nbsp  "&#160;">
<!ENTITY  space  "&#x20;">
<!ENTITY  br  "&#x2028;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="main" margin="5mm 5mm 5mm 5mm"
                    page-width="297mm" page-height="210mm">
                    <fo:region-before extent="5mm" margin="0pt"/>
                    <fo:region-after extent="5mm" margin="0pt"/>
                    <fo:region-start extent="5mm" margin="0pt"/>
                    <fo:region-end extent="5mm" margin="0pt"/>
                    <fo:region-body margin="5mm 5mm 10mm 5mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="main">
                <!-- ENTETE de page -->
                <fo:static-content flow-name="xsl-region-before"/>
                <!-- ENTETE de page : END -->
                <!-- BAS de page -->
                <fo:static-content flow-name="xsl-region-after"
                    font-family="Arial, Helvetica, sans-serif" font-size="7pt"/>
                <!-- BAS de page : END -->
                <!--AVANT -->
                <fo:static-content flow-name="xsl-region-start"/>
                <!--AVANT: END -->
                <!--APRES -->
                <fo:static-content flow-name="xsl-region-end"/>
                <!--APRES: END -->
                <!-- PAGE CONTENTS -->
                <fo:flow flow-name="xsl-region-body" font-family="Arial, Helvetica, sans-serif"
                    font-size="8pt" font-color="#000000">
                    <xsl:apply-templates select="entete"/>
                </fo:flow>
                <!-- PAGE CONTENTS : END-->
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- Debut du planning-->
    <xsl:template match="entete">
        <xsl:variable name="nbHeureAffiche">
            <xsl:value-of select="affichageHeure/nbsHeuresAffichees"/>
        </xsl:variable>
        <fo:table width="100%" height="100%" border-right="1pt solid" border-left="1pt solid"
            border-top="1pt solid" border-bottom="1pt solid">
            <!-- Nombre de colonne variable-->
            <xsl:call-template name="recursiveCols">
                <xsl:with-param name="nb" select="$nbHeureAffiche"/>
            </xsl:call-template>
            <!-- Nombre de colonne variable END-->
            <fo:table-body>
                <!--Entête du planning -->
                <fo:table-row>
                    <fo:table-cell border-right="1pt solid" border-bottom="1 pt solid"
                        text-align="center" display-align="center" font-size="12pt">
                        <fo:block>Jours de la semaine</fo:block>
                    </fo:table-cell>
                    <fo:table-cell number-columns-spanned="{$nbHeureAffiche}-1">
                        <fo:table>
                            <fo:table-column/>
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell text-align="center" font-size="20pt"
                                        font-family="Verdana" font-color="black">
                                        <fo:block>
                                            <xsl:value-of select="diplome"/>
                                        </fo:block>
                                        <fo:block font-size="12pt">
                                            <xsl:value-of select="semaine"/>
                                        </fo:block>
                                        <fo:block font-size="5pt"> &nbsp; </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <!--Affichage des heures avec les paramêtres-->
                                        <xsl:apply-templates select="affichageHeure">
                                            <xsl:with-param name="heureDeDepart" select="affichageHeure/heureDepart"/>
                                            <xsl:with-param name="nbHeure" select="$nbHeureAffiche"/>
                                        </xsl:apply-templates>
                                        <!--Affichage des heures avec les paramêtres END-->
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </fo:table-cell>
                </fo:table-row>
                <!--Entête du planning END -->
                <!--Affichage des créneaux du jour -->
                <xsl:for-each select="jour">
                    <fo:table-row>
                        <!-- affichage du nom du jour et de sa date ainsi que les # groupes-->
                        <fo:table-cell border-bottom="1 pt solid" border-right="1pt solid"
                            display-align="center" text-align="center" font-size="12pt" font-family="Verdana">
                            <xsl:apply-templates select="groupes"/>
                        </fo:table-cell>
                        <!-- affichage graphique des creneaux horaires-->
                        <fo:table-cell number-columns-spanned="{$nbHeureAffiche}-1" height="100%"
                            display-align="center" text-align="center">
                            <fo:block>
                                <!--  liste des cours d'une journnée-->
                                <xsl:apply-templates select="listeCoursJournnee">
                                    <xsl:with-param name="nbHeure" select="$nbHeureAffiche"/>
                                </xsl:apply-templates>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </xsl:for-each>
                <!--Affichage des créneaux du jour END -->
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- Affichage des heures-->
    <xsl:template match="affichageHeure">
        <xsl:param name="heureDeDepart" select="0"/>
        <xsl:param name="nbHeure" select="0"/>
        <xsl:param name="nb" select="$nbHeure"/>
        <fo:table>
            <!-- Nombre de colonne variable-->
            <xsl:call-template name="recursiveCols">
                <xsl:with-param name="nb" select="$nb"/>
            </xsl:call-template>
            <!-- Nombre de colonne variable END-->
            <fo:table-body>
                <fo:table-row>
                    <!-- appel au template recursif pour l'affichage des heures-->
                    <xsl:call-template name="recursiveAfficheHeure">
                        <xsl:with-param name="i" select="0"/>
                        <xsl:with-param name="max" select="$nbHeure"/>
                        <xsl:with-param name="heure" select="$heureDeDepart"/>
                    </xsl:call-template>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- On divise l'espace en 1/4 heure -->
    <xsl:template match="listeCoursJournnee">
        <xsl:param name="nbHeure" select="0"/>
        <xsl:variable name="nbCreneau" select="nbCreneau"/>
        <fo:table width="100%">
            <!-- Utilisation de la template recursive pour les colonnes-->
            <xsl:call-template name="recursiveCols">
                <!-- x*4 car granularité = 1/4 heure-->
                <xsl:with-param name="nb" select="$nbHeure*4"/>
            </xsl:call-template>
            <fo:table-body>
                <!-- affichage des créneaux-->
                <xsl:apply-templates select="creneau">
                    <xsl:with-param name="nbCreneau" select="$nbCreneau"/>
                </xsl:apply-templates>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- template recursive permettant de cree un nombre préci de colonne-->
    <xsl:template name="recursiveCols">
        <xsl:param name="nb" select="0"/>
        <xsl:choose>
            <xsl:when test="$nb &gt; 0">
                <fo:table-column/>
                <xsl:call-template name="recursiveCols">
                    <xsl:with-param name="nb" select="($nb)-1"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- template recursive permettant l'affichage des heures -->
    <xsl:template name="recursiveAfficheHeure">
        <xsl:param name="i" select="0"/>
        <!-- Position en cours-->
        <xsl:param name="max" select="0"/>
        <!-- nbMax d'heures-->
        <xsl:param name="heure" select="0"/>
        <!-- heure en cours-->
        <fo:table-cell border-right="1pt solid" border-top="1pt solid" border-bottom="1pt solid"
            font-size="10pt" margin-left="1pt" margin-top="1pt" margin-bottom="0.2pt">
            <fo:block>
                <fo:table border="0pt">
                    <fo:table-column/>
                    <fo:table-column/>
                    <fo:table-body>
                        <fo:table-row>
                            <!-- affichage de l'heure calculé-->
                            <fo:table-cell number-rows-spanned="2">
                                <fo:block>
                                    <xsl:value-of select="$heure"/>h </fo:block>
                            </fo:table-cell>
                            <fo:table-cell>
                                <fo:block> &nbsp;</fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                        <fo:table-row>
                            <!-- affichage du trai de la demi-heure-->
                            <fo:table-cell border-left="1pt solid" font-size="5pt">
                                <fo:block>&nbsp;</fo:block>
                            </fo:table-cell>
                        </fo:table-row>
                    </fo:table-body>
                </fo:table>
            </fo:block>
        </fo:table-cell>
        <xsl:choose>
            <xsl:when test="$i &lt; $max">
                <!-- on rappella même fonction-->
                <xsl:call-template name="recursiveAfficheHeure">
                    <xsl:with-param name="i" select="$i+1"/>
                    <xsl:with-param name="max" select="$max"/>
                    <xsl:with-param name="heure" select="($heure)+1"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- template pour l'affichage d'un creneau horraire -->
    <xsl:template match="creneau">
        <xsl:param name="nbCreneau" select="nbCreneau"/>
        <xsl:variable name="heightCell" select="2.6 div $nbCreneau"/>
        <fo:table-row display-align="center" height="{$heightCell}cm">
            <!-- pour chaque cours-->
            <xsl:for-each select="cours">
                <xsl:variable name="colspan">
                    <xsl:value-of select="width"/>
                </xsl:variable>
                <xsl:variable name="rowspan">
                    <xsl:value-of select="height"/>
                </xsl:variable>
                <xsl:variable name="couleurFond"><xsl:value-of select="color"/>
                </xsl:variable>
                <fo:table-cell background-color="#{$couleurFond}" border-bottom="1 pt solid"
                    border-right="1pt solid" border-top="1pt solid" border-left="1pt solid"
                    margin-top="1pt" number-columns-spanned="{$colspan}" number-rows-spanned="{$rowspan}">
                    <!-- Si il y un seul cours sur le même creneau, il prend toute la largeur, taille police = 12-->
                    <xsl:choose>
                        <xsl:when test="($rowspan div $nbCreneau) = 1">
                            <fo:block font-size="12pt" font-family="Verdana" font-color="black"
                                text-align="center" display-align="center">
                                <!-- Affichage des infos du cours-->
                                <fo:block>
                                    <xsl:value-of select="texte/contenu"/>
                                </fo:block>
                                <fo:block>
                                    <xsl:value-of select="texte/occupants"/>
                                </fo:block>
                                <fo:block>
                                    <xsl:value-of select="texte/salles"/>
                                </fo:block>
                            </fo:block>
                        </xsl:when>
                        <xsl:otherwise>
                            <!-- plus il y a de cours dans le même creneau, plus on diminue la taille de la police-->
                            <xsl:variable name="fontSize" select="22 * ($rowspan div $nbCreneau)"/>
                            <fo:block font-size="{$fontSize}pt" font-family="Verdana"
                                font-color="black" text-align="center" display-align="center">
                                <!-- Affichage des infos du cours-->
                                <fo:block>
                                    <xsl:value-of select="texte/contenu"/>
                                </fo:block>
                                <fo:block>
                                    <xsl:value-of select="texte/occupants"/> &nbsp;&nbsp;
                                        <xsl:value-of select="texte/salles"/>
                                </fo:block>
                            </fo:block>
                        </xsl:otherwise>
                    </xsl:choose>
                </fo:table-cell>
            </xsl:for-each>
        </fo:table-row>
    </xsl:template>
    <!-- utilisation SVG   <xsl:template match="creneau">
        <xsl:variable name="posX">
            <xsl:value-of select="x"/>
        </xsl:variable>
        <xsl:variable name="posY">
            <xsl:value-of select="y"/>
        </xsl:variable>
        <xsl:variable name="width">
            <xsl:value-of select="width"/>
        </xsl:variable>
        <xsl:variable name="height">
            <xsl:value-of select="heigth"/>
        </xsl:variable>
        <xsl:variable name="couleurFond"> #<xsl:value-of select="color"/>
        </xsl:variable>
        <fo:instream-foreign-object>
            <svg xmlns="http://www.w3.org/2000/svg"  x="0" y="0" width="20cm" height="3cm">
                <rect x="0" y="0" width="{$width}" height="{$height}" style="fill:red"/>
                <text x="5" y="15" lengthAdjust="spacing" font="menu" style="font-weight:100;font-family:Verdana;font-size:12pt;fill:black;stroke:none;">
                    <xsl:value-of select="texte/contenu"/>
                    <xsl:value-of select="texte/occupants"/>
                </text>
                <text x="5" y="35" style="font-weight:100;font-family:Verdana;font-size:8pt;fill:black;stroke:none;">
                    <xsl:value-of select="texte/salles"/>
                </text>
            </svg>
        </fo:instream-foreign-object>
    </xsl:template>-->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- Affichage vertical du jour et de la date en SVG-->
    <xsl:template name="afficheNomEtDateJour">
        <xsl:param name="jour" select="jour"/>
        <xsl:param name="date" select="date"/>
        <fo:instream-foreign-object>
            <svg xmlns="http://www.w3.org/2000/svg" width="2cm" height="2.4cm">
                <!-- le jour-->
                <text x="15" y="55" lengthAdjust="spacing" font="menu"
                    style="font-weight:100;font-family:Verdana;font-size:10pt;fill:black;stroke:none;" transform="rotate(-90,15,55)">
                    <xsl:value-of select="$jour"/>
                </text>
                <!-- la date-->
                <text x="25" y="55" lengthAdjust="spacing" font="menu"
                    style="font-weight:100;font-family:Verdana;font-size:8pt;fill:grey;stroke:none;" transform="rotate(-90,25,55)">
                    <xsl:value-of select="$date"/>
                </text>
            </svg>
        </fo:instream-foreign-object>
    </xsl:template>
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- -->
    <!-- affichage des differents groupes et du jour et de la date-->
    <xsl:template match="groupes">
        <fo:table width="100%">
            <fo:table-column/>
            <fo:table-column/>
            <fo:table-body>
                <xsl:variable name="nbGroupe" select="nbGroupe"/>
                <fo:table-row>
                    <fo:table-cell text-align="left" display-align="middle">
                        <fo:block>
                            <!-- affichage graphique (SVG) du jour et de la date-->
                            <xsl:call-template name="afficheNomEtDateJour">
                                <xsl:with-param name="jour" select="nomDuJour"/>
                                <xsl:with-param name="date" select="dateDuJour"/>
                            </xsl:call-template>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                        <fo:block>
                            <fo:table>
                                <fo:table-column/>
                                <fo:table-body>
                                    <!-- affichage des groupes-->
                                    <xsl:for-each select="nomGroupe">
                                        <xsl:variable name="heightCell" select="(2.6 div $nbGroupe)"/>
                                        <fo:table-row height="{$heightCell}cm">
                                            <fo:table-cell border-top="1 pt solid" border-left="1pt solid">
                                                <fo:block font-size="10pt" text-align="center" display-align="middle">
                                                  <xsl:value-of select="name"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>
</xsl:stylesheet>
