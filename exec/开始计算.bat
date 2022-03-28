@echo off
title handleExcel
set path=%cd%\jre\bin
%path%\java.exe -jar handleExcel.jar ./初始数据.xlsx ./结果数据.xlsx ./过程数据.xlsx