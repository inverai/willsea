# coding=utf-8
import os
path=input('�������ļ�·��(��β����/)��')       

#��ȡ��Ŀ¼�������ļ��������б���
f=os.listdir(path)

n=0
for i in f:
    oldname=path+f[n]
    newname=path+''+str(n+1)+'.PNG'
    os.rename(oldname,newname)
    n+=1
