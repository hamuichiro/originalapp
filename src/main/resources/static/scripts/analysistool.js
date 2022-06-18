      const list = document.querySelectorAll(".list");

      function activeLink() {
        list.forEach((item) => {
          item.classList.remove("active");
        });
        this.classList.add("active");
      }
      list.forEach((item) => {
        item.addEventListener("click", activeLink)
      });

//勝率の円グラフ
var ctx = document.getElementById('myChart');
var myChart = new Chart(ctx, {
  type: 'pie',
  data: {  
      labels: [
	    '勝ち',
	    '負け',
	    '引き分け'
	  ],
	  datasets: [{
	    label: 'My First Dataset',
	    data: [60, 39, 1],
	    backgroundColor: [	      
		  'rgb(54, 162, 235)',
	      'rgb(255, 99, 132)',
	      'rgb(255, 205, 86)'
	    ]
	  }]
  },
  options: {}
});

//利益の線グラフ
  var ctx = document.getElementById("myLineChart");
  var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: ['1日', '2日', '3日', '4日', '5日', '6日', '7日', '8日', '9日', '10日', '11日', '12日', '13日', '14日', '15日', '16日', '17日'],
      datasets: [
        {
       
          data: [-100, 0, 100],
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




//表の追加

var tableEle = document.getElementById('data-table');
for (var i = 0; i < 5; i++) {
  // テーブルの行を 5行追加する
  var tr = document.createElement('tr');
  for (var j = 0; j < 3; j++) {
    // テーブルの列を 3行追加する
    var td = document.createElement('td');
    td.innerHTML = 'データ'+(i+1)+"-"+(j+1);
    tr.appendChild(td);
  }
  tableEle.appendChild(tr);
}