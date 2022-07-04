



$(document).ready(function(){
    //デフォルトで表示する要素を指定
    $('.dropdown__list').hide();
    

    //buttonがクリックされたら処理を実行
    $('.title').click(function () {

         //toggleClassでshowのclassを追加または削除
        $('.dropdown__lists').toggleClass('show');

        //#appがshowのclassをを持っていれば
        if($('.dropdown__lists').hasClass('show')){

             //要素の表示を切り替える

            $('.dropdown__list').show();
        }else{

             //要素の表示を切り替える
            $('.dropdown__list').hide();
        }
    });
    
});


      
//ローディング画面      
$('#button').click(function(){
$(function() {
  var h = $(window).height();
  
  $('#wrap').css('display','none');
  $('#loader-bg ,#loader').height(h).css('display','block');
});
  
$(window).load(function () { //全ての読み込みが完了したら実行
  $('#loader-bg').delay(900).fadeOut(800);
  $('#loader').delay(600).fadeOut(300);
  $('#wrap').css('display', 'block');
});
});




//取引データ一覧表
$(function () {

  function drawTabla(transactionDataList, i) {
	
    var newDate = (transactionDataList[i].transactionNewDate).substr(3, 5);
    var settlementDate = (transactionDataList[i].transactionNewDate).substr(3, 5);
  
    var trTag = $("<tr>");
  
    trTag.append($("<td></td>").text(newDate));
    trTag.append($("<td></td>").text(transactionDataList[i].transactionNewTime));
    trTag.append($("<td></td>").text(transactionDataList[i].currencyPair));
    trTag.append($("<td></td>").text(transactionDataList[i].transactionType));
    trTag.append($("<td></td>").text(transactionDataList[i].transactionLot));
    trTag.append($("<td></td>").text(transactionDataList[i].rateNew));
    trTag.append($("<td></td>").text(settlementDate));
    trTag.append($("<td></td>").text(transactionDataList[i].transactionSettlementTime));
    trTag.append($("<td></td>").text(transactionDataList[i].rateSettlement));
    trTag.append($("<td></td>").text(transactionDataList[i].rateDifference));
    trTag.append($("<td></td>").text(transactionDataList[i].profitLoss));
    trTag.append($("<td></td>").text(transactionDataList[i].profitlossParseint));
    trTag.append($("<td></td>").text(transactionDataList[i].profitLossConfirm));
    trTag.append($("<td></td>").text(""));
    trTag.append($("<td></td>").text(""));
    trTag.append($("<td></td>").text(""));
    $('#tableChart').append(trTag);			
  }

  $("#dateList_month").click(function() {
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
	  if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      
      var setMonth = 6;
      var month;
        
      $('#tableChart').find("tr:gt(0)").remove();
      
      for (var i = 0; i < transactionDataList.length; i ++) {
		month = (transactionDataList[i].transactionNewDate).substr(3,2);
		if(month == setMonth) {
		  drawTabla(transactionDataList, i);	
		}
      }
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
  
  $("#dateList_year").click(function() {
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
	  if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      
      var setYear = 22;
      var year;
      
      $('#tableChart').find("tr:gt(0)").remove();
      for (var i = 0; i < transactionDataList.length; i ++) {
		year = (transactionDataList[i].transactionNewDate).substr(0,2);
		if(year == setYear) {
		  drawTabla(transactionDataList, i);		
		}
      }
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
  
  $("#dateList_all").click(function() {
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
	  if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
        
      $('#tableChart').find("tr:gt(0)").remove();
      for (var i = 0; i < transactionDataList.length; i ++) {
		drawTabla(transactionDataList, i);	
      }
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
});





//勝率の円グラフ
$('#pieChartMonth').hide();
$('#pieChartYear').hide();
$('#pieChartAll').hide();
 $(function () {  
	

function drawPiechart(winRate, myPieChart) {

  var ctx = document.getElementById(myPieChart);
 
  var myPieChart = new Chart(ctx, {
    type: 'pie',
    data: {  
      labels: [
	    '勝ち',
	    '負け',
	    '引き分け'
	  ],
	  datasets: [{
	    label: 'My First Dataset',
	    data: [winRate[0], winRate[1], winRate[2]],
	    backgroundColor: [	      
		  'rgb(54, 162, 235)',
	      'rgb(255, 99, 132)',
	      'rgb(255, 205, 86)'
	    ]
	  }]
    },
    options: {}
  });
}
 

		$('#closeButton_pieChartMonth').click(function () {
		$('#pieChartMonth').hide();
	});
		$('#closeButton_pieChartYear').click(function () {
		$('#pieChartYear').hide();
	});
		$('#closeButton_pieChartAll').click(function () {
		$('#pieChartAll').hide();
	});



  $("#piechart_month").click(function() {

    $('#pieChartMonth').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var winRate = new Array(3);
      winRate.fill(0);
      
      
      var setMonth = 5;
      var month;
      for (var i = 0; i < transactionDataList.length; i ++) {
		month = (transactionDataList[i].transactionNewDate).substr(4,1);
		if(month == setMonth) {
	      if(transactionDataList[i].rateDifference > 0) {
	        winRate[0]++;
		  }
		  else if (transactionDataList[i].rateDifference < 0){
		    winRate[1]++;
		  }
		  else {
		    winRate[2]++;
		  }
		}
	  }	    
	  
	  drawPiechart(winRate, "myPieChartMonth");
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })



  $("#piechart_year").click(function() {
	$('#pieChartYear').show();

	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var winRate = new Array(3);
      winRate.fill(0);
      
      
      var setYear = 22;
      var year;
      
      for (var i = 0; i < transactionDataList.length; i ++) {
		year = (transactionDataList[i].transactionNewDate).substr(0,2);
		if(year == setYear) {
	      if(transactionDataList[i].rateDifference > 0) {
	        winRate[0]++;
		  }
		  else if (transactionDataList[i].rateDifference < 0){
		    winRate[1]++;
		  }
		  else {
		    winRate[2]++;
		  }
		}
	  }	    
	 
	  drawPiechart(winRate, "myPieChartYear");
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })



  $("#piechart_all").click(function() {
$('#pieChartAll').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var winRate = new Array(3);
      winRate.fill(0);
      

      for (var i = 0; i < transactionDataList.length; i ++) {
	    if(transactionDataList[i].rateDifference > 0) {
	      winRate[0]++;
		}
		else if (transactionDataList[i].rateDifference < 0){
		  winRate[1]++;
		}
		else {
		  winRate[2]++;
		}
	  }	    
	  
	  drawPiechart(winRate, "myPieChartAll");
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
});
      


//利益の線グラフ
$('#lineChartMonth').hide();
$('#lineChartYear').hide();
$('#lineChartAll').hide();

$(function () {
	
  function drawLinechart(profitLoss, transactionNumberList, myLineChart) {
	
  var ctx = document.getElementById(myLineChart);
  
  var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: transactionNumberList,
      datasets: [
	  {
        data: profitLoss,
        borderColor: "rgb(54, 162, 235)",
        backgroundColor: "rgb(54, 162, 235, 0.5)",
        pointStyle: 'circle',
	    pointRadius: 5,
	    pointHoverRadius: 10
      },
      ],
    },
    options: {
      responsive: true,
      plugins: {
        title: {
          display: true,
          text: (ctx) => 'Point Style: ' + ctx.chart.data.datasets[0].pointStyle,
        }  
      }
    },
  });
}

		$('#closeButton_lineChartMonth').click(function () {
		$('#lineChartMonth').hide();
	});
		$('#closeButton_lineChartYear').click(function () {
		$('#lineChartYear').hide();
	});
		$('#closeButton_lineChartAll').click(function () {
		$('#lineChartAll').hide();
	});

  $("#linechart_month").click(function() {
	$('#lineChartMonth').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var transactionNumber = transactionDataList.length;
      var profitLoss = [];
      profitLoss.push(0);
      
      var sum = 0;
      var labelNumber = 0;
      var transactionNumberList = [];
      transactionNumberList.push(0);
      
      var setMonth = 5;
      var month;
      
      for (var i = 0; i < transactionNumber; i ++) {
		month = (transactionDataList[i].transactionNewDate).substr(4,1);
	    if (month == setMonth) {
			sum += Number(transactionDataList[i].profitLossConfirm);
			profitLoss.push(sum);
			labelNumber++;
			transactionNumberList.push(labelNumber);
		}
		
	  }
	  drawLinechart(profitLoss, transactionNumberList, "myLineChartMonth");

	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
  
  $("#linechart_year").click(function() {
	$('#lineChartYear').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var transactionNumber = transactionDataList.length;
      var profitLoss = [];
      profitLoss.push(0);
      
      var sum = 0;
      var labelNumber = 0;
      var transactionNumberList = [];
      transactionNumberList.push(0);
      
      var setYear = 22;
      var year;
      
      for (var i = 0; i < transactionNumber; i ++) {
		year = (transactionDataList[i].transactionNewDate).substr(0,2);
		  if(year == setYear) {
		    sum += Number(transactionDataList[i].profitLossConfirm);
		    profitLoss.push(sum);
		    labelNumber++;
		    transactionNumberList.push(labelNumber);
		  }
	  }
	  drawLinechart(profitLoss, transactionNumberList, "myLineChartYear");

	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })

  $("#linechart_all").click(function() {
	$('#lineChartAll').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var transactionNumber = transactionDataList.length;
      var profitLoss = [];
      profitLoss.push(0);
      
      var sum = 0;
      var transactionNumberList = [...Array(transactionNumber + 1).keys()].map(i => i);
      for (var i = 0; i < transactionNumber; i ++) {
		sum += Number(transactionDataList[i].profitLossConfirm);
		profitLoss.push(sum);
	  }
	  drawLinechart(profitLoss, transactionNumberList, "myLineChartAll");

	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
});


//棒グラフ
$('#barChartCurrency').hide();
$('#barChartWeek').hide();
$('#barChartTime').hide();
$(function() {
  
  function drawBarchart(labels, profit, loss, myBarChart) {
	var ctx = document.getElementById(myBarChart);
    new Chart(ctx, {
      type: "bar",
      data: {
        labels:  labels, 
        datasets: [
          {
            label: "profit",                    
            data: profit,               
            backgroundColor: "red"                    
          },
          {                                               
            label: "loss",                            
            data: loss,                 
            backgroundColor: "blue"                     
          }                                               
        ]
      },
      options: {
        //responsive: false,  
        legend: {                // 凡例の表示位置
          position: 'bottom'
        },                
        scales: {
          yAxes: [
            {
              ticks: {
                min: 0
              }
            }
          ]
        }
      }
    })
  }
  
  		$('#closeButton_barChartCurrency').click(function () {
		$('#barChartCurrency').hide();
	});
		$('#closeButton_barChartWeek').click(function () {
		$('#barChartYear').hide();
	});
		$('#closeButton_barChartTime').click(function () {
		$('#barChartTime').hide();
	});
	
  $("#barchart_currency").click(function() {
	$('#barChartCurrency').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var currencyPair_profit = new Array(10);
      var currencyPair_loss = new Array(10);
      currencyPair_profit.fill(0);
      currencyPair_loss.fill(0);
      
      var labels = ["EURUSD", "EURJPY", "EURGBP", "EURAUD", "USDJPY", "GBPUSD", "GBPJPY", "GBPAUD", "AUDUSD", "AUDJPY"];

      for (var i = 0; i < transactionDataList.length; i ++) {
	    for (var j = 0; j < labels.length; j ++) {
		  if(transactionDataList[i].currencyPair == labels[j]) {
	        if(transactionDataList[i].rateDifference > 0) {
	          currencyPair_profit[j]++;
		    }
		    else {
		      currencyPair_loss[j]++;
		    }
		  }
		}
	  }
	  drawBarchart(labels, currencyPair_profit, currencyPair_loss, "myBarChartCurrency");
    })
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  });
  
  $("#barchart_week").click(function() {
	$('#barChartWeek').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var weekOfDay_profit = new Array(7);
      var weekOfDay_loss = new Array(7);
      weekOfDay_profit.fill(0);
      weekOfDay_loss.fill(0);
      
      var labels = ["SUN", "MON", "TUE", "WED", "TUE", "FRI", "SAT"];
      

      for (var i = 0; i < transactionDataList.length; i ++) {
		var transactionDate = transactionDataList[i].transactionNewDate;
		var setYear = "20" + transactionDate.substr(0, 2);
		var setMonth = transactionDate.substr(3, 2) - 1;
		var setDay = transactionDate.substr(6, 2);
		var date = new Date(setYear, setMonth, setDay);
		
	    for (var j = 0; j < labels.length; j ++) {
		  if(date.getDay() == j) {
	        if(transactionDataList[i].rateDifference > 0) {
	          weekOfDay_profit[j]++;
		    }
		    else {
		      weekOfDay_loss[j]++;
		    }
		  }
		}
	  }
	  drawBarchart(labels, weekOfDay_profit, weekOfDay_loss, "myBarChartWeek");
    })
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  });
  
  $("#barchart_time").click(function() {
	$('#barChartTime').show();
	$.ajax({
      url: '/dateList',
      dataType: 'json',
      type: "GET"
    })
    .done(function (transactionDataList) {
      if(!transactionDataList){
        alert("該当するデータはありませんでした");
        return;
      }
      var time_profit = new Array(24);
      var time_loss = new Array(24);
      time_profit.fill(0);
      time_loss.fill(0);
      
      var labels = new Array(24);
      let num = 0;
      for (var i = 0; i < labels.length; i++) {
	    if(i < 10) {
		  labels[i] = "0" + num;
		}
		else {
		  labels[i] = num;
		}
		num++;
	  }
      
      
      for (var i = 0; i < transactionDataList.length; i ++) {
	  var hour = (transactionDataList[i].transactionNewTime).substr(0, 2);
	    for (var j = 0; j < labels.length; j ++) {
		  if(hour == labels[j]) {
	        if(transactionDataList[i].rateDifference > 0) {
	          time_profit[j]++;
		    }
		    else {
		      time_loss[j]++;
		    }
		  }
		}
	  }
	  drawBarchart(labels, time_profit, time_loss, "myBarChartTime");
    })
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
});



     





