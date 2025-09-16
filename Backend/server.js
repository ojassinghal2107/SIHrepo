const express = require("express");
const cors = require("cors");
const csv = require("csv-parser");
const multer = require("multer");
const { Readable } = require("stream");
const dbConnect = require("./database/db"); 
const DataModel = require("./database/Schema/studSchema");
const app = express();
app.use(cors());
dbConnect()


const upload = multer({ storage: multer.memoryStorage() });


app.get("/", (req, res) => {
  res.send("Hello World!");
}   );

app.post("/upload", upload.single("file"), (req, res) => {
  const results = [];
  const stream = Readable.from(req.file.buffer);

  stream
    .pipe(csv())
    .on("data", (row) => {
      results.push(row);
    })
    .on("end", async () => {
      try {
        await DataModel.insertMany(results);
        res.json({ message: "CSV uploaded & saved to MongoDB", data: results });
      } catch (err) {
        res.status(500).json({ message: "Error saving to DB", error: err.message });
      }
    });
});



app.listen(3000, () => {
  console.log("Server started on port 3000");
});