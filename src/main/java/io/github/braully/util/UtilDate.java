/*
 Copyright 2109 Braully Rocha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
 */
package io.github.braully.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author braullyrocha
 */
public class UtilDate {

    public final static long OFFSET_SEGUNDO = 1000;
    public final static long OFFSET_MINUTO = (60 * OFFSET_SEGUNDO);
    public final static long OFFSET_HORA = (60 * OFFSET_MINUTO);
    public final static long OFFSET_DIA = (24 * OFFSET_HORA);
    public final static long OFFSET_MES = (30 * OFFSET_DIA);
    public final static long OFFSET_ANO = OFFSET_DIA * 365;
    /**
     *
     */
    protected static String[] datePatternsDMY = new String[]{"dd-MM-yyyy", "dd-MM-yy", "dd/MM/yyyy", "dd/MM/yy"};
    protected static String[] datePatternsDM = new String[]{"dd-MM", "dd-MM", "dd/MM", "dd/MM"};
    protected static String[] datePatternsD = new String[]{"dd-MM", "dd-MM", "dd/MM", "dd/MM"};

    /*
     *
     */
    public static Date parseData(String texto) {
        if (UtilValidation.isStringEmpty(texto)) {
            return null;
        }
        Date data = null;
        data = parseDataDMY(texto);
        if (data == null) {
            data = parseDataDM(texto);
        }
        return data;
    }

    public static int anoAtual() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR);
    }

    public static int getDiaData(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMesData(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.MONTH);
    }

    public static int getAnoData(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        return c.get(Calendar.YEAR);
    }

    protected static Date parseDataDM(String texto) {
        Date data = null;
        for (String pattern : datePatternsDM) {
            try {
                data = parseData(pattern, texto);
                Calendar c = Calendar.getInstance();
                c.setTime(data);
                c.set(Calendar.YEAR, anoAtual());
                data = c.getTime();
                break;
            } catch (ParseException e) {
            }
        }
        return data;
    }

    protected static Date parseDataDMY(String texto) {
        Date data = null;
        for (String pattern : datePatternsDMY) {
            try {
                data = parseData(pattern, texto);
                break;
            } catch (ParseException e) {
            }
        }
        return data;
    }

    public static Date parseData(String pattern, String texto) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(texto);
    }

    public static String formatData(String pattern, Date data) {
        if (data == null) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(data);
    }

    public static String formatData(Date data) {
        return formatData("dd/MM/yyyy", data);
    }

    public static Date primeiroDiaMes(Date data) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date ultimoDiaMes(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    public static void main(String[] args) {
        long diferencaDia = diferencaDia(parseData("26/08/2015"), parseData("25/09/2015"));
        System.out.println(diferencaDia);
//        System.out.println(formatData(parseData("10-10")));
//        System.out.println(formatData(parseData("10-10-12")));
//        System.out.println(formatData(parseData("10/10")));
//        System.out.println(formatData(parseData("10/OUT")));
//        System.out.println(formatData(parseData("10/10/2012")));
//
//        int bimestre = 0;
//        int mesData = 0;
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 1;
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 2;
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 3;
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 4;
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 5;
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 6;
//        bimestre = mesData / 2;
//        if (mesData > 6) {
//            mesData--;
//        }
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 7;
//        if (mesData > 6) {
//            mesData--;
//        }
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 8;
//        if (mesData > 6) {
//            mesData--;
//        }
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 9;
//        if (mesData > 6) {
//            mesData--;
//        }
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 10;
//        if (mesData > 6) {
//            mesData--;
//        }
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
//        mesData = 11;
//        if (mesData > 6) {
//            mesData--;
//        }
//        bimestre = mesData / 2;
//        System.err.println(mesData + "=" + bimestre);
    }

    public static String formatarDataExtenso(Date data) {
        String ret = null;
        if (data != null) {
            StringBuilder sb = new StringBuilder();
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);
            sb.append(dia);
            sb.append(" de ");
            String mesStr = "";
            switch (mes) {
                case Calendar.JANUARY:
                    mesStr = "Janeiro";
                    break;
                case Calendar.FEBRUARY:
                    mesStr = "Fevereiro";
                    break;
                case Calendar.MARCH:
                    mesStr = "Março";
                    break;
                case Calendar.APRIL:
                    mesStr = "Abril";
                    break;
                case Calendar.MAY:
                    mesStr = "Maio";
                    break;
                case Calendar.JUNE:
                    mesStr = "Junho";
                    break;
                case Calendar.JULY:
                    mesStr = "Julho";
                    break;
                case Calendar.AUGUST:
                    mesStr = "Agosto";
                    break;
                case Calendar.SEPTEMBER:
                    mesStr = "Setembro";
                    break;
                case Calendar.OCTOBER:
                    mesStr = "Outubro";
                    break;
                case Calendar.NOVEMBER:
                    mesStr = "Novembro";
                    break;
                case Calendar.DECEMBER:
                    mesStr = "Dezembro";
                    break;
                default:
                    break;
            }
            sb.append(mesStr);
            sb.append(" de ");
            sb.append(ano);
            ret = sb.toString();
        }
        return ret;
    }

    public static long getAnoAtual() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR);
    }

    public static long diferencaMiliSegundos(Date dateFim, Date dataIni) {
        return Math.abs(dateFim.getTime() - dataIni.getTime());
    }

    public static long diferencaAno(Date dateFim, Date dataIni) {
        return diferencaMiliSegundos(dateFim, dataIni) / OFFSET_ANO;
    }

    public static long diferencaMes(Date dateFim, Date dataIni) {
        return diferencaMiliSegundos(dateFim, dataIni) / OFFSET_MES;
    }

    public static long diferencaDia(Date dateFim, Date dataIni) {
        return diferencaMiliSegundos(dateFim, dataIni) / OFFSET_DIA;
    }

    public static int getDiaSemana(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static int getBimestreData(Date dataAula) {
        int bimestre = 0;
        int mesData = getMesData(dataAula);
        if (mesData > 6) {
            mesData--;
        }
        bimestre = mesData / 2;
        return bimestre;
    }

    public static Date aplicarExpressaoData(String expressaoDataPagamento, Date dataPagamento) {
        return dataPagamento;
    }

    public static Date aplicarExpressaoData(String expressaoDataPagamento, Integer i, Date dataPagamento) {
        return dataPagamento;
    }

    public static boolean isHoje(Date data) {
        boolean ret = false;
        if (data != null) {
            Date hoje = new Date();
            if (hoje.getDay() == data.getDay() && hoje.getMonth() == data.getMonth() && hoje.getYear() == data.getYear()) {
                ret = true;
            }
        }
        return ret;
    }

    public static boolean isAntesHoje(Date dataVencimento) {
        Date hoje = new Date();
        boolean ret = false;
        if (dataVencimento != null) {
            long diff = hoje.getTime() - dataVencimento.getTime();
            ret = diff >= OFFSET_DIA;
        }
        return ret;
    }

    public static Date parseDataTime(String strData) {
        Date data = null;
        if (strData != null && !strData.trim().isEmpty()) {
            try {
                long time = Long.parseLong(strData.trim());
                data = new Date(time);
            } catch (Exception e) {

            }
        }
        return data;
    }

    public static Date parseData(int ano, int mes, int dia) {
        Date data = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        data = calendar.getTime();
        return data;
    }
}
