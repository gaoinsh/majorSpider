package com.mybatis.main;


import com.spider.common.utils.RegexFilter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class MybatisGeneratorMain {

    @Test
    public void generatorAll() throws IOException {

        //select 所有的column
        FileOutputStream fout = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\2.txt");

        InputStreamReader reader = new InputStreamReader(new FileInputStream("C:\\Users\\Administrator\\Desktop\\1.txt"), Charset.forName("UTF-8"));
        BufferedReader bufferReader = new BufferedReader(reader);
        StringBuilder resultMap = new StringBuilder();
        StringBuilder columnAll = new StringBuilder();
        StringBuilder propertyAll = new StringBuilder();
        String buffer;
        resultMap.append("<resultMap id=\"BaseResultMap\" type=\"\">\n");
        String primaryKey = null;
        while ((buffer = bufferReader.readLine()) != null) {
            String column = RegexFilter.regexFilter(buffer, "`([^`]+)`\\s+[^\\(]+");
            String type = RegexFilter.regexFilter(buffer, "`[^`]+`\\s+([^\\(]+?)(?:\\s+|\\()");
            String primaryKeyTmp = RegexFilter.regexFilter(buffer, "PRIMARY\\s+KEY\\s+\\(`([^`]+)`\\)");

            if (StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(type)) {
                String propertyName = toCamelCase(column);
                String mySqlType = getType(type);
                String l = "    <result column=\"" + column + "\" jdbcType=\"" + mySqlType +
                        "\" property=\"" + propertyName + "\"/>\n";
                resultMap.append(l);
                columnAll.append(column).append(",");
                propertyAll.append("private ").append(mySqlType).append(" ").append(propertyName).append(";\n");
            }
            if (StringUtils.isNotEmpty(primaryKeyTmp)) {
                primaryKey = primaryKeyTmp;
            }

        }

        resultMap.append("</resultMap>");

        String outStr = resultMap.toString();
        if (StringUtils.isNotEmpty(primaryKey)) {
            String primaryKeyColumn = RegexFilter.regexFilter(outStr, "(<result\\s+column=\"" + primaryKey + "[\\s\\S]*?/>)");
            outStr = outStr.replace(primaryKeyColumn, primaryKeyColumn.replace("<result column", "<id column"));
        }

        fout.write("===========result Map start===========".getBytes());
        fout.write(outStr.getBytes());
        fout.write("===========result Map end===========".getBytes());

        fout.write("===========column start===========".getBytes());
        fout.write(columnAll.substring(0, columnAll.length() - 1).getBytes());
        fout.write("===========column end===========".getBytes());

        fout.write("===========property start===========".getBytes());
        fout.write(propertyAll.toString().getBytes());
        fout.write("===========property end===========".getBytes());

        bufferReader.close();
        fout.close();
    }

    public static void main(String[] args) {
        String avatarUrl = toCamelCase("audit_desc");
        System.out.println(avatarUrl);
    }

    private String getType(String type) {
        String s = type.toUpperCase();
        if (s.equals("INT")) {
            s = "INTEGER";
        }
        return s;
    }

    public static String toCamelCase(String column) {
        List<String> strings = RegexFilter.regexFilterList(column, "(_\\w)");
        for (String str : strings) {
            String s = str.replace("_", "").toUpperCase();
            column = column.replace(str, s);
        }
        return column;
    }


}