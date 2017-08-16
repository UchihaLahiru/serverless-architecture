var express = require('express')
var app = express()
var bodyParser = require('body-parser')
app.use(bodyParser.json());
app.get('/', function (req, res) {
  res.send('working')
})
app.post('/', function (req, res) {
  res.send(req.body)
})
app.listen(8082, function () {
  console.log('Example app listening on port 8082!')
})
