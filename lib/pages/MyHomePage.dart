import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:thirty_day_of_flutter/models/photo.dart';
import 'package:thirty_day_of_flutter/widgets/drawer.dart';
import 'package:thirty_day_of_flutter/widgets/item_widgets.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final int days = 30;
  final String name = "CodePur";

  @override
  void initState() {
    super.initState();
    loadData();
  }

  loadData() async {
  await  Future.delayed(Duration(seconds: 2));
    final photoJson = await rootBundle.loadString("assets/files/photo.json");
    final decodedData = jsonDecode(photoJson);
    PhotoModel.photos = List.from(decodedData).map<Photo>((photo) => Photo.fromMap(photo)).toList();
    setState(() {

    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text("30 Days of Flutter"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: (PhotoModel.photos!=null && PhotoModel.photos.isNotEmpty)? ListView.builder(
          itemCount: PhotoModel.photos.length,
          itemBuilder: (context, index) {
            return PhotoWidget(photo: PhotoModel.photos[index]);
          },
        ):Center(
          child: CircularProgressIndicator(),
        ),
      ),

      drawer: MyDrawer(),
    );
  }
}
