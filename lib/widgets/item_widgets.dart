import 'package:flutter/material.dart';
import 'package:thirty_day_of_flutter/models/photo.dart';

class PhotoWidget extends StatelessWidget {
  final Photo photo;

  const PhotoWidget({Key key, @required this.photo})
      : assert(photo != null),
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      // color: Colors.green,
      child: ListTile(
        onTap: () {
          print("${photo.title} pressed");
        },
        leading: Image.network(photo.url),
        title: Text(photo.title,style: TextStyle(
          color: Colors.black,
          fontWeight: FontWeight.bold,
        ),),
        subtitle: Text(photo.id.toString()),
        trailing: Image.network(photo.thumbnailUrl),
      ),
    );
  }
}
