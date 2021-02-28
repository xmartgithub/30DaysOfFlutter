import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  final int days = 30;
  final String name = "CodePur";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text("30 Days of flutter"),
      ),
      body: Center(
        child: Text("I am learning $days days of flutter form $name"),
      ),
      drawer: Drawer(),
    );
  }
}
