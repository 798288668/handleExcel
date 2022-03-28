@echo off
title handleExcel
set path=%cd%\jre\bin
%path%\java.exe -jar handleExcel1.jar ./过程数据.xlsx ./根据过程数据生成的结果数据.xlsx