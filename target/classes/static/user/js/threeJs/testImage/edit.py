# coding=utf-8
import os
path=input('请输入文件路径(结尾加上/)：')       

#获取该目录下所有文件，存入列表中
f=os.listdir(path)

n=0
for i in f:
    oldname=path+f[n]
    newname=path+''+str(n+1)+'.PNG'
    os.rename(oldname,newname)
    n+=1
