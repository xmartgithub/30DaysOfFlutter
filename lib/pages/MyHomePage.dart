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
    await Future.delayed(Duration(seconds: 2));
    final photoJson = await rootBundle.loadString("assets/files/photo.json");
    final decodedData = jsonDecode(photoJson);
    PhotoModel.photos = List.from(decodedData)
        .map<Photo>((photo) => Photo.fromMap(photo))
        .toList();
    setState(() {});
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
        child: (PhotoModel.photos != null && PhotoModel.photos.isNotEmpty)
            ? GridView.builder(
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                  mainAxisSpacing: 16.0,
                  crossAxisSpacing: 16.0,
                ),
                itemBuilder: (context, index) {
                  final photo = PhotoModel.photos[index];
                  return Card(
                    clipBehavior: Clip.antiAlias,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    //Day 16 half grid view done
                    child: GridTile(
                      header: Container(
                        child: Text(photo.title),
                        padding: const EdgeInsets.all(12.0),
                        decoration: BoxDecoration(
                          color: Colors.amber,
                        ),
                      ),
                      child: Image.network(photo.url),
                      footer: Container(
                        child: Text(
                          photo.id.toString(),
                          style: TextStyle(
                            color: Colors.white,
                          ),
                        ),
                        padding: const EdgeInsets.all(12.0),
                        decoration: BoxDecoration(
                          color: Colors.black,
                        ),
                      ),
                    ),
                  );
                },
                itemCount: PhotoModel.photos.length,
              )
            : Center(
                child: CircularProgressIndicator(),
              ),
      ),
      drawer: MyDrawer(),
    );
  }
}
