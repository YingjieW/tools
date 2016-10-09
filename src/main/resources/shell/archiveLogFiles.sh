#!/bin/sh
echo "----------> $0 start."
source="/apps/log/tomcat-nohup"
target="/apps/log/tomcat-nohup/archive"
if [ ! -d $source ]; then
        echo "$source does not exist!"
        exit 0
fi
if [ ! -d $target ]; then
        echo "$target does not exist, create $target"
        mkdir $target
fi
regex="^cs-(model|process)-core-2016-[0-1][0-9]-[0-3][0-9].log$"
for source_file in ${source}/*;
        do
                temp_file_name=`basename $source_file`
                temp_file_path=${source}"/"${temp_file_name}
                if [ -d $temp_file_path ]; then
                        #echo "$temp_file_name is a directory"
                        :
                elif [ -f $temp_file_path ]; then
                        echo $temp_file_name | egrep -q $regex
                        if [ $? -eq 0 ]; then
                                echo "---> move : $temp_file_name"
                                mv -i $temp_file_path $target
                        fi
                else
                        echo "$temp_file_name is nothing"
                fi
        done
echo "----------> $0 end."
