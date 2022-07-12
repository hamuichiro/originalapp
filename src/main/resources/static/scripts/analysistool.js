$('.title').hide();


$(function(){
  $(".menue_list").mouseenter(function(){
	($(this).children().children().children(".title")).stop().slideDown();
	$(this).css('background-color','rgb(18,18,18)');
	$(this).css('box-shadow','0 7px 14px 0 rgba(255,255,255, 0.3), 0 3px 6px 0 rgba(255,255,255, 0.3)');
	$(this).css('width','220px');

  });
    $(".menue_list").mouseleave(function(){
	($(this).children(".dropdown__lists")).stop().slideUp("fast");
    $(this).css('border-radius','15px 15px 15px 15px');
	($(this).children().children().children(".title")).stop().slideUp();
	$(this).css('background-color','');
	$(this).css('box-shadow', '');
	$(this).css('width', '');

  });

	
  $(".slide-btn").click(function(){
    ($(this).next(".dropdown__lists")).stop().slideToggle();
    ($(this).parent(".menue_list")).css('border-radius','15px 15px 0 15px');
    
  });

    $(".menue_list").mouseleave(function(){

    
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
    var transactionNumber = transactionDataList[i].transactionNumber;
    var path = "/transactionNumber?transactionNumber=" + transactionNumber;
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
    trTag.append($('<td><a href="" id="setId">表示</a></td>'));  
    $('#tableChart').append(trTag);
    $('#setId').attr('href', path);
    $('#setId').attr('id', transactionNumber);
    
	
	//$('#transactionNumber').click(function() {
		
		//console.log(transactionNumber);
	//});
    	

  }
  

  
  
  $(function() {
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
$('#pieChartLong').hide();
$('#pieChartShort').hide();
$('#pieChartMonth').hide();
$('#pieChartYear').hide();
$('#pieChartAll').hide();

$('#closeButton_pieChartLong').click(function () {
  $('#pieChartLong').hide();
});
$('#closeButton_pieChartShort').click(function () {
  $('#pieChartShort').hide();
});
$('#closeButton_pieChartMonth').click(function () {
  $('#pieChartMonth').hide();
});
$('#closeButton_pieChartYear').click(function () {
  $('#pieChartYear').hide();
});
$('#closeButton_pieChartAll').click(function () {
  $('#pieChartAll').hide();
});
	
 $(function () {  
	
  var title;
  var chartType;
  
  var winRate = [];
	

function drawPiechart(winRate, chartType) {
	
  var myPieChart;
  if(chartType == 0) {
	myPieChart = "myPieChartLong";
	title = "Long勝率";
　　}
  else if(chartType == 1) {
	myPieChart = "myPieChartShort";
	title = "Short勝率";
　　}
  else if(chartType == 2) {
	myPieChart = "myPieChartMonth";
	title = "月間勝率";
　　}
  else if(chartType == 3) {
	myPieChart = "myPieChartYear";
	title = "年間勝率";
　　}
  else {
	myPieChart = "myPieChartAll";
	title = "総勝率";
　　}

  var ctx = document.getElementById(myPieChart);
 
  var myPieChart = new Chart(ctx, {
    type: 'pie',
    data: {  
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
    options: {
      responsive: true,
      plugins: {
        title: {
          display: true,
          text: title
        }  
      }
    }
  });
}

  $("#piechart_long").click(function() {
	$('#pieChartLong').show();
	

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
      
      chartType = 0;
      winRate = new Array(3);
      winRate.fill(0);
      
      
      var position;
      
      for (var i = 0; i < transactionDataList.length; i ++) {
		position = (transactionDataList[i].transactionType);
		if(position == "Long") {
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
	 
	  drawPiechart(winRate, chartType);
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
  
  $("#piechart_short").click(function() {
	$('#pieChartShort').show();

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
      
      chartType = 1;
      winRate = new Array(3);
      winRate.fill(0);
      
      
      var position;
      
      for (var i = 0; i < transactionDataList.length; i ++) {
		position = (transactionDataList[i].transactionType);
		if(position == "Short") {
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
	 
	  drawPiechart(winRate, chartType);
	})
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
 
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
      
      chartType = 2;
      winRate = new Array(3);
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
	  
	  drawPiechart(winRate, chartType);
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
      
      chartType = 3;
      winRate = new Array(3);
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
	 
	  drawPiechart(winRate, chartType);
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
      
      chartType = 4;
      winRate = new Array(3);
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
	  
	  drawPiechart(winRate, chartType);
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

$('#closeButton_lineChartMonth').click(function () {
	$('#lineChartMonth').hide();
});
$('#closeButton_lineChartYear').click(function () {
	$('#lineChartYear').hide();
});
$('#closeButton_lineChartAll').click(function () {
	$('#lineChartAll').hide();
});

$(function () {
  
  var title;
  var chartType;
  
  var sum;
  var labelNumber;
  
  var transactionNumber;
  
  var profitLoss = [];
  
  var transactionNumberList = [];

  
	
	function drawLinechart(profitLoss, transactionNumberList, chartType) {
		
	  var myLineChart;
	  if(chartType == 0) {
		myLineChart = "myLineChartMonth";
		title = "月間損益";
	　　}
	  else if(chartType == 1) {
		myLineChart = "myLineChartYear";
		title = "年間損益";
	　　}
	  else {
		myLineChart = "myLineChartAll";
		title = "総損益";
	　　}
		
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
	          text: title
	        }  
	      }
	    }
	  });
	}


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
      chartType = 0;
      transactionNumber = transactionDataList.length;
      
      profitLoss = [];
      profitLoss.push(0);
      
      transactionNumberList = [];
      transactionNumberList.push(0);
      
      sum = 0;
      labelNumber = 0;

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
	  drawLinechart(profitLoss, transactionNumberList, chartType);

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
      chartType = 1;
      transactionNumber = transactionDataList.length;
      
      profitLoss = [];
      profitLoss.push(0);
      
      transactionNumberList = [];
      transactionNumberList.push(0);
      
      sum = 0;
      labelNumber = 0;

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
	  drawLinechart(profitLoss, transactionNumberList, chartType);

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
      
      chartType = 2;
      transactionNumber = transactionDataList.length;
      
      profitLoss = [];
      profitLoss.push(0);

      transactionNumberList = [...Array(transactionNumber + 1).keys()].map(i => i);
      
      sum = 0;

      
      for (var i = 0; i < transactionNumber; i ++) {
		sum += Number(transactionDataList[i].profitLossConfirm);
		profitLoss.push(sum);
	  }
	  drawLinechart(profitLoss, transactionNumberList, chartType);

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

$('#closeButton_barChartCurrency').click(function () {
  $('#barChartCurrency').hide();
});
$('#closeButton_barChartWeek').click(function () {
  $('#barChartYear').hide();
});
$('#closeButton_barChartTime').click(function () {
  $('#barChartTime').hide();
});
	
$(function() {
  
  var chartType;
  var title;
  
  var profit;
  var loss;
  
  var labels = [];
  
  function drawBarchart(labels, profit, loss, chartType) {
	
	var myBarChart;
  	if(chartType == 0) {
	  myBarChart = "myBarChartCurrency";
	  title = "通貨別勝敗";
　　	}
  	else if(chartType == 1) {
	  myBarChart = "myBarChartWeek";
	  title = "曜日別勝敗";
　　	}
  	else {
	  myBarChart = "myBarChartTime";
	  title = "時間別勝敗";
　　	}
	
	
	var ctx = document.getElementById(myBarChart);
    new Chart(ctx, {
      type: "bar",
      data: {
        labels:  labels, 
        datasets: [
          {
                               
            data: profit,               
            backgroundColor: "red"                    
          },
          {                                               
                                       
            data: loss,                 
            backgroundColor: "blue"                     
          }                                               
        ]
      },
	    options: {
	      responsive: true,
	      plugins: {
	        title: {
	          display: true,
	          text: title
	        }  
	      }
	    }
    })
  }
  

	
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
      chartType　= 0;
      
      profit = new Array(10);
      loss = new Array(10);
      profit.fill(0);
      loss.fill(0);
      
      labels = ["EURUSD", "EURJPY", "EURGBP", "EURAUD", "USDJPY", "GBPUSD", "GBPJPY", "GBPAUD", "AUDUSD", "AUDJPY"];

      for (var i = 0; i < transactionDataList.length; i ++) {
	    for (var j = 0; j < labels.length; j ++) {
		  if(transactionDataList[i].currencyPair == labels[j]) {
	        if(transactionDataList[i].rateDifference > 0) {
	          profit[j]++;
		    }
		    else {
		      loss[j]++;
		    }
		  }
		}
	  }
	  drawBarchart(labels, profit, loss, chartType);
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
      chartType = 1;
      profit = new Array(7);
      loss = new Array(7);
      profit.fill(0);
      loss.fill(0);
      
      labels = ["SUN", "MON", "TUE", "WED", "TUE", "FRI", "SAT"];
      

      for (var i = 0; i < transactionDataList.length; i ++) {
		var transactionDate = transactionDataList[i].transactionNewDate;
		var setYear = "20" + transactionDate.substr(0, 2);
		var setMonth = transactionDate.substr(3, 2) - 1;
		var setDay = transactionDate.substr(6, 2);
		var date = new Date(setYear, setMonth, setDay);
		
	    for (var j = 0; j < labels.length; j ++) {
		  if(date.getDay() == j) {
	        if(transactionDataList[i].rateDifference > 0) {
	          profit[j]++;
		    }
		    else {
		      loss[j]++;
		    }
		  }
		}
	  }
	  drawBarchart(labels, profit, loss, chartType);
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
      chartType= 2;
      profit = new Array(24);
      loss = new Array(24);
      profit.fill(0);
      loss.fill(0);
      
      labels = new Array(24);
      
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
	          profit[j]++;
		    }
		    else {
		      loss[j]++;
		    }
		  }
		}
	  }
	  drawBarchart(labels, profit, loss, chartType);
    })
	.fail(function () {
      alert("ファイルが読み込めませんでした");
    });
  })
});



     





