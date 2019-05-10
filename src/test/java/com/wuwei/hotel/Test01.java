package com.wuwei.hotel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Test01{
        void testPost(String urlStr) throws IOException {
             try{
        URL url=new URL(urlStr);
        URLConnection con=url.openConnection();
        con.setDoOutput(true);
        con.setRequestProperty("Pragma:","no-cache");
        con.setRequestProperty("Cache-Control","no-cache");
        con.setRequestProperty("Content-Type","text/xml");

        OutputStreamWriter out=new OutputStreamWriter(con
        .getOutputStream());
        String xmlInfo = getXmlInfo();
        System.out.println("urlStr="+urlStr);
        System.out.println("xmlInfo="+xmlInfo);
        out.write(new String(xmlInfo.getBytes("ISO-8859-1")));
        out.flush();
        out.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(con
        .getInputStream()));
        String line ="";
        for (line = br.readLine();line != null; line = br.readLine()) {
        System.out.println(line);
        }
        } catch (MalformedURLException e){
        e.printStackTrace();
        }catch(IOException e){
        e.printStackTrace();
        }
        }

        private String getXmlInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("<peticion>");
        sb.append("<tipo>110</tipo>");
        sb.append("<nombre>Servicio de disponibilidad por lista de hoteles</nombre>");
        sb.append("<agencia>Agencia Prueba</agencia>");
        sb.append("<parametros>");
        sb.append("<hotel>745388#</hotel>");
        sb.append("<pais>MV</pais>");
        sb.append("<pais_cliente>ES</pais_cliente>");
        sb.append("<categoria>0</categoria>");
        sb.append("<fechaentrada>08/15/2018</fechaentrada>");
        sb.append("<fechasalida>08/16/2018</fechasalida>");
        sb.append("<afiliacion>RS</afiliacion>");
                sb.append("<usuario>XXXXXXXX</usuario>");
                sb.append("<numhab1>1</numhab1>");
                sb.append("<paxes1>2-0</paxes1>");
                sb.append("<edades1></edades1>");
                sb.append("<numhab2>0</numhab2>");
                sb.append("<paxes2>2-0</paxes2>");
                sb.append("<edades2></edades2>");
                sb.append("<numhab3>0</numhab3>");
                sb.append("<paxes3>2-0</paxes3>");
                sb.append("<edades3></edades3>");
                sb.append("<idioma>1</idioma>");
                sb.append("<informacion_hotel>0</informacion_hotel>");
                sb.append("<tarifas_reembolsables>0</tarifas_reembolsables>");
                sb.append("<comprimido>2</comprimido>");
                sb.append("</parametros>");
                sb.append("</peticion>");
        return sb.toString();
        }

        public static void main(String[] args) throws IOException {
            String url ="http://xml.hotelresb2b.com/xml/listen_xml.jsp";
            new Test01().testPost(url);
        }
        }
