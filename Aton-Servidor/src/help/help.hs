<?xml version="1.0" encoding="ISO-8859-1"?>  
<helpset>  
        <title>Autentia Real Business Solutions. Tutorial JavaHelp</title>  
        <maps>  
                <!-- Página por defecto al mostrar la ayuda -->  
                <homeID>introduction</homeID>  
                <!-- Que mapa deseamos -->  
                <mapref location="help.jhm" />  
        </maps>  
  
        <!-- Las Vistas que deseamos mostrar en la ayuda -->  
        <view>  
                <!-- Deseamos una tabla de contenidos -->  
                <name>Content</name>  
                <!-- El tooltiptext de la vista -->  
                <label>Tabla de contenidos</label>  
                <type>javax.help.TOCView</type>  
                <!-- El icono que se muesta -->  
                <image>ContentIco</image>  
                <!-- El fichero que la define -->  
                <data>ayudaTOC.xml</data>  
        </view>  
  
        <view xml:lang="es">  
                <!-- Deseamos que se puedan realizar búsquedas -->  
                <name>Search</name>  
                <!-- El tooltiptext -->  
                <label>B�squeda</label>  
                <!-- El icono que se muesta -->  
                <image>SearchIco</image>  
                <type>javax.help.SearchView</type>  
                <data engine="com.sun.java.help.search.DefaultSearchEngine">  
                        JavaHelpSearch  
                </data>  
        </view>  
  
        <!-- Definición de la ventana principal de la ayuda-->  
        <presentation default="true" displayviews="true" displayviewimages="true">  
                <name>MainWin</name>  
                <!-- Dimensiones iniciales -->  
                <size width="640" height="480" />  
                <!-- Posición inicial -->  
                <location x="200" y="200" />  
                <!-- Título de la ventana -->  
                <title>Ayuda</title>  
                <!-- Definimos la barra de herramientas de la ventana -->  
                <toolbar>  
                        <!-- Permitimos ir a la página anterior -->  
                        <helpaction image="BackwardIco">  
                                javax.help.BackAction  
                        </helpaction>  
                        <!-- Permitimos ir a la página siguiente -->  
                        <helpaction image="ForwardIco">  
                                javax.help.ForwardAction  
                        </helpaction>  
                        <!-- Permitimos imprimir el contenido -->  
                        <helpaction image="PrintIco">  
                                javax.help.PrintAction  
                        </helpaction>  
                        <!-- Permitimos configurar la impresión -->  
                        <helpaction image="PrintSetupIco">  
                                javax.help.PrintSetupAction  
                        </helpaction>  
                </toolbar>  
        </presentation>  
</helpset>  