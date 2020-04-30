import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'GUIs Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  int _counter = 0;
  bool inCel = false;
  bool inFahr = false;
  double _cel = 0, _fahr = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  void _decrementCounter(){
    setState(() {
      _counter--;
    });
  }

  @override
  Widget build(BuildContext context) {
    Row row1 = Row(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        RaisedButton(
          onPressed: _decrementCounter,
          child: const Text(
            "Decrement",
            style: TextStyle(fontSize: 20)
          ),
        ),
        Container(
          width: 120,
          alignment: Alignment.topCenter,
          child: Text(
            '$_counter',
            style: TextStyle(fontSize: 20),
          ),
        ),
        RaisedButton(
          onPressed: _incrementCounter,
          child: const Text(
            "Increment",
            style: TextStyle(fontSize: 20)
          ),
        ),
      ],
    );
    
    Row row3 = Row(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        RaisedButton(
          onPressed: _decrementCounter,
          child: const Text(
            "Decrement",
            style: TextStyle(fontSize: 20)
          ),
        ),
        Container(
          width: 120,
          alignment: Alignment.topCenter,
          child: Text(
            '$_counter',
            style: TextStyle(fontSize: 20),
          ),
        ),
        RaisedButton(
          onPressed: _incrementCounter,
          child: const Text(
            "Increment",
            style: TextStyle(fontSize: 20)
          ),
        ),
      ],
    );

    Row row2 = Row(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        RaisedButton(
            onPressed: _incrementCounter,
            child: const Text(
              "Increment",
              style: TextStyle(fontSize: 20)
            ),
          ),
      ],
    );
    
    return new MaterialApp(
      home:  Container(
        alignment: Alignment.topCenter,
        width: 600,
        height: 1200,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget> [
            Container(
              width: 600,
              height: 60,
              child: row1,
            ),
            row2,
          ],
        )
      )
    );
  }
}