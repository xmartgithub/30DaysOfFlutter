import 'package:flutter/material.dart';

class LoginPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Text(
          "Login Page",
          style: TextStyle(
            // fontSize: 30.0,
            fontWeight: FontWeight.bold,
            color: Colors.orange,
          ),
          textScaleFactor: 2.0,
        ),
      ),
    );
  }
}
