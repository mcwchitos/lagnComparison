import 'package:flutter/material.dart';
import 'dart:math' as math;

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

class FirstRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('First Route'),
      ),
      body: Center(
        child: RaisedButton(
          child: Text('Open route'),
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => SecondRoute()),
            );
          },
        ),
      ),
    );
  }
}

class ForthRoute extends StatefulWidget {
  @override
  _CountDownTimerState createState() => _CountDownTimerState();
}

class _CountDownTimerState extends State<ForthRoute>
    with TickerProviderStateMixin {
  AnimationController controller;
  double seconds = 5;

  String get timerString {
    Duration duration = controller.duration * controller.value;
    return '${duration.inMinutes}:${(duration.inSeconds % 60).toString().padLeft(2, '0')}';
  }

  @override
  void initState() {
    super.initState();
    controller = AnimationController(
      vsync: this,
      duration: Duration(seconds: seconds.toInt()),
    );
  }

  @override
  Widget build(BuildContext context) {
    ThemeData themeData = Theme.of(context);
    return 
    Column(children: <Widget>[
      Expanded(
        flex: 1,
        child: 
          Scaffold(
            body: 
              Slider(
              min: 1,
              max: 15,
              divisions: 14,
              value: seconds,
              onChanged: (value) {
                setState(() {
                  seconds = value;
                  controller.duration = Duration(seconds: seconds.toInt());
                  print(seconds);
                });
              },
            ),
          )
      ),
      Expanded(
        flex: 9,
        child: Scaffold(
      backgroundColor: Colors.white10,
      body:
      AnimatedBuilder(
          animation: controller,
          builder: (context, child) {
            return Stack(
              children: <Widget>[
                // Align(
                //   alignment: Alignment.bottomCenter,
                //   child: Container(
                //     color: Colors.amber,
                //     height:
                //         controller.value * MediaQuery.of(context).size.height,
                //   ),
                // ),
                Padding(
                  padding: EdgeInsets.all(8.0),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Expanded(
                        child: Align(
                          alignment: FractionalOffset.center,
                          child: AspectRatio(
                            aspectRatio: 1.0,
                            child: Stack(
                              children: <Widget>[
                                Positioned.fill(
                                  child: CustomPaint(
                                      painter: CustomTimerPainter(
                                        animation: controller,
                                        backgroundColor: Colors.white,
                                        color: themeData.indicatorColor,
                                      )),
                                ),
                                // Align(
                                //   alignment: FractionalOffset.center,
                                //   child: Column(
                                //     mainAxisAlignment:
                                //         MainAxisAlignment.spaceEvenly,
                                //     crossAxisAlignment:
                                //         CrossAxisAlignment.center,
                                //     children: <Widget>[
                                //       Text(
                                //         "Count Down Timer",
                                //         style: TextStyle(
                                //             fontSize: 20.0,
                                //             color: Colors.white),
                                //       ),
                                //       Text(
                                //         timerString,
                                //         style: TextStyle(
                                //             fontSize: 112.0,
                                //             color: Colors.white),
                                //       ),
                                //     ],
                                //   ),
                                // ),
                              ],
                            ),
                          ),
                        ),
                      ),
                      AnimatedBuilder(
                          animation: controller,
                          builder: (context, child) {
                            return FloatingActionButton.extended(
                                onPressed: () {
                                  if (controller.isAnimating)
                                    controller.stop();
                                  else {
                                    controller.reverse(
                                        from: controller.value == 0.0
                                            ? 1.0
                                            : controller.value);
                                  }
                                },
                                icon: Icon(controller.isAnimating
                                    ? Icons.pause
                                    : Icons.play_arrow),
                                label: Text(
                                    controller.isAnimating ? "Pause" : "Play"));
                          }),
                    ],
                  ),
                ),
              ],
            );
          }),
    ),)
    
    ]
    );
  }
}

class CustomTimerPainter extends CustomPainter {
  CustomTimerPainter({
    this.animation,
    this.backgroundColor,
    this.color,
  }) : super(repaint: animation);

  final Animation<double> animation;
  final Color backgroundColor, color;

  @override
  void paint(Canvas canvas, Size size) {
    Paint paint = Paint()
      ..color = backgroundColor
      ..strokeWidth = 10.0
      ..strokeCap = StrokeCap.butt
      ..style = PaintingStyle.stroke;

    canvas.drawCircle(size.center(Offset.zero), size.width / 2.0, paint);
    paint.color = color;
    double progress = (1.0 - animation.value) * 2 * math.pi;
    canvas.drawArc(Offset.zero & size, math.pi * 1.5, -progress, false, paint);
  }

  @override
  bool shouldRepaint(CustomTimerPainter old) {
    return animation.value != old.animation.value ||
        color != old.color ||
        backgroundColor != old.backgroundColor;
  }
}

/// This Widget is the main application widget.
class ThirdRoute extends StatelessWidget {
  static const String _title = 'Flutter Code Sample';

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: _title,
      home: Scaffold(
        appBar: AppBar(title: const Text(_title)),
        body: Center(
          child: MyDropStateful(),
        ),
      ),
    );
  }
}

class MyDropStateful extends StatefulWidget {
  MyDropStateful({Key key}) : super(key: key);

  @override
  DropStateful createState() => DropStateful();
}

class DropStateful extends State<MyDropStateful> {
  String dropdownValue = 'One-Way';
  bool enabled = false;
  final date1 = TextEditingController();
  final date2 = TextEditingController();

  @override
  void initState() {
    super.initState();

  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        DropdownButton<String>(
          value: dropdownValue,
          icon: Icon(Icons.arrow_downward),
          iconSize: 24,
          elevation: 16,
          style: TextStyle(color: Colors.deepPurple),
          underline: Container(
            height: 2,
            color: Colors.deepPurpleAccent,
          ),
          onChanged: (String newValue) {
            setState(() {
              if (newValue == 'Two-Way')
                enabled = true;
              else
                enabled = false;
              dropdownValue = newValue;
            });
          },
          items: <String>['One-Way', 'Two-Way']
              .map<DropdownMenuItem<String>>((String value) {
            return DropdownMenuItem<String>(
              value: value,
              child: Text(value),
            );
          }).toList(),
        ),
        Container(
          width: 280,
          padding: EdgeInsets.all(10.0),
          child: TextField(
              keyboardType: TextInputType.datetime,
              // controller: celToFahr,
              decoration: InputDecoration(hintText: 'Enter first date Here'),
            ),
          ),
        Container(
          width: 280,
          padding: EdgeInsets.all(10.0),
          child: TextField(
            keyboardType: TextInputType.datetime,
            enabled: enabled,
            // controller: celToFahr,
            decoration: InputDecoration(hintText: 'Enter second date Here'),
          ),
        ),
        RaisedButton(
          child: Text('book'),
          onPressed: () {},
        ),
        RaisedButton(
          child: Text('Next Page'),
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => ForthRoute()),
            );
          },
        ),
      ]
    );

  }
}


class SecondRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
        appBar: AppBar(
        title: Text('Converter')),
        body: Center(
          child: TextFieldCustom()
          )
        )
      );
  }
}

class TextFieldCustom extends StatefulWidget  {

  TextFieldWidget createState() => TextFieldWidget();  

}

class TextFieldWidget extends State {

  final celToFahr = TextEditingController();
  final fahrToCel = TextEditingController();

  bool inCel = false;
  bool inFahr = false;

  @override
  void initState() {
    super.initState();

    celToFahr.addListener(_celToFahr);
    fahrToCel.addListener(_fahrToCel);
  }

  _celToFahr(){
    if (inFahr)
      return;
    inCel = true;
    fahrToCel.text = (9/5 * (double.parse(celToFahr.text) + 32)).toString();
    inCel = false;
  }
  _fahrToCel(){
    if (inCel)
      return;
    inFahr = true;
    celToFahr.text = (5/9 * (double.parse(fahrToCel.text) - 32)).toString();
    inFahr = false;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[

             Container(
              width: 280,
              padding: EdgeInsets.all(10.0),
              child: TextField(
                  keyboardType: TextInputType.number,
                  controller: celToFahr,
                  decoration: InputDecoration(hintText: 'Enter Celsius here'),
                ),
              ),

              Container(
              width: 280,
              padding: EdgeInsets.all(10.0),
              child: TextField(
                  keyboardType: TextInputType.number,
                  controller: fahrToCel,
                  decoration: InputDecoration(hintText: 'Enter Fahrenheit here'),
                ),
              ),
              RaisedButton(
                child: Text('Next Page'),
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => ThirdRoute()),
                  );
                },
              ),
            ],
          ),
        ));
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
          child: Text('Next Page'),
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => SecondRoute()),
            );
          },
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