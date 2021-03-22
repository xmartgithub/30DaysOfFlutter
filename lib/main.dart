import 'package:flutter/material.dart';
import 'package:thirty_day_of_flutter/pages/MyHomePage.dart';
import 'package:thirty_day_of_flutter/pages/login_page.dart';
import 'package:thirty_day_of_flutter/utils/routes.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      themeMode: ThemeMode.system,
      theme: ThemeData(primarySwatch: Colors.deepPurple),
      darkTheme: ThemeData(primarySwatch: Colors.red),
      initialRoute: "/",
      routes: {
        "/": (context) => LoginPage(),
        MyRoutes.homeRoute: (context) => HomePage(),
        MyRoutes.loginRoute: (context) => LoginPage(),
      },
    );
  }
}
