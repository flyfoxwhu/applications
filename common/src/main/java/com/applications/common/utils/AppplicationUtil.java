package com.applications.common.utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class AppplicationUtil {
    private static final Logger log = LoggerFactory.getLogger(AppplicationUtil.class);

    /**
     * 导出excle
     * @param sheetMapList 导出的数据源头支持导出多个sheet
     *                     比如导出两个sheet:
     *                     List<Map<String,Object>> sheetMapList=new ArrayList()
     *                     map key:sheet, list,
     * @param fileName 导出的文件名
     * @param response
     */
    public static void exportExcel(List<Map> sheetMapList, String fileName, HttpServletResponse response) {
        OutputStream out = null;
        try {
            Workbook wb = new XSSFWorkbook();
            Font font = wb.createFont();
            CellStyle cellStyle = wb.createCellStyle();
            font.setFontHeightInPoints((short) 16);//设置字体大小
            cellStyle.setFont(font);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中

            for (Map m : sheetMapList) {
                Sheet sheet = wb.createSheet(ChangeUtil.getString("sheet", m));
                List<List<String>> list = (List<List<String>>) m.get("list");
                for (int j = 0; j < list.size(); j++) {//行
                    Row row = sheet.createRow((short) j);
                    List<String> l = list.get(j);
                    for (int i = 0; i < l.size(); i++) {//列
                        Cell cell = row.createCell(i);
                        if (ChangeUtil.isNumber(String.valueOf(l.get(i)))) {
                            cell.setCellValue(Double.valueOf(String.valueOf(l.get(i))));
                        } else {
                            cell.setCellValue(l.get(i));
                        }
                        cell.setCellStyle(cellStyle);
                    }
                }
            }

            response.reset();
            response.setContentType("application/ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + java.net.URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
            out = response.getOutputStream();

            wb.write(out);
            out.flush();
            out.close();

        } catch (IOException e) {
            log.error("excel error", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    log.error("excel close error", e);
                }
            }
        }
    }


    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] arr = StringUtils.split(ip, ",");
        if (arr != null && arr.length > 0) {
            return arr[0].trim();
        }
        return ip;
    }



}
