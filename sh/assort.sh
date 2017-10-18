#!/bin/bash
# FS=$'\n'
 
for f in "$@"
do
     
fpath=${f%/*} #ファイルのパスを取得
ext=${f##*.} #ファイルの拡張子を取得

echo $fpath
echo $ext
     
if [ ! $ext = "app" -a ! -d $f ]; then
        
  if [ ! -e $fpath/$ext -o ! -d $fpath/$ext ]; then
    mkdir $fpath/$ext
  fi  
    mv $f $fpath/$ext
  fi
                
done
