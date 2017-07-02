function getOption(mustJson,jsonData){
	
	option = {
		    title : {
		        text: mustJson.title,
		        subtext: '纯属虚构',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: jsonData[0].one
		    },
		    series : [
		        {
		            name: '占比',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:jsonData[1].two,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};

	
	return option;
}