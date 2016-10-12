#!/bin/sh
# 作者：yingjie.wang
# 用途：归档log文件
echo ""
echo "----------> $0 start."
echo ""

source="/Users/YJ/Documents/test_only"
target="/Users/YJ/Documents/test_only/archive"
log=$target"/archive.log"
forceCover=$1

if [ ! -d $source ]; then
	echo "$source does not exist! EXIT!"
	exit 1
fi

if [ ! -d $target ]; then
	echo "---> [$target] does not exist, create [$target]"
	mkdir $target
fi

if [ ! -f $log ] ; then
	echo "---> [$log] does not exist, create [$log]"
	touch $log
fi

writeToLog() {
	msg=$1;
	echo "`date +%F.%T` : $msg" >> $log
}

isFileExistInTarget() {
	file=$target"/"$1
	if [ -f $file ]; then
		return 1
	else
		return 0
	fi
}

regex="^cs-(model|process)-core-2016-[0-1][0-9]-[0-3][0-9].log$"
for source_file in ${source}/*;
	do
		temp_file_name=`basename $source_file`
		temp_file_path=${source}"/"${temp_file_name}
		if [ -d $temp_file_path ]; then
			:
		elif [ -f $temp_file_path ]; then
			echo $temp_file_name | egrep -q $regex
			if [ $? -eq 0 ]; then
				isFileExistInTarget "$temp_file_name"
				if [ $? -eq 1 ]; then
					msg="file[$temp_file_name] already exists in [$target], PLEASE CHECK!!!"
					echo "===> "$msg
					writeToLog "$msg"
				else
					msg="move [$temp_file_name] to [$target]"
					echo "---> "$msg
					writeToLog "$msg"
					mv $temp_file_path $target
				fi
			fi
		else
			echo "[$temp_file_name] is werid, suggest to check [$temp_file_name]"
		fi
	done

echo ""
echo "----------> $0 end."
echo ""
