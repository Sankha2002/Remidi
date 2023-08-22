require('../src/conn');

const nodemailer = require('nodemailer');
const date = require('date-and-time');
const express = require('express');
const cors = require('cors');
const multer = require('multer');
const cron = require('node-cron');
const bodyparser = require("body-parser");
const { check, validationResult } = require('express-validator');
// const axios = require('axios');

const healthcare_management = require("./patientDetails.model.js");
const user_details = require("./userDetails.model");

const app = express()
const port = 8000;
var emailAddress;
var reportDate;
var medicineBuy = [];
var medicineBuyOptions = [];
var medBuy;
const options = {};

app.use(bodyparser.json(), cors());
app.use(bodyparser.urlencoded({ extended: true }));
app.use('/uploads', express.static('uploads'))

cron.schedule("0 0 */5 * * *", async function () {
    var databaseFetch = await healthcare_management.find();
    // console.log(databaseFetch)
    let emailArr = databaseFetch.map(element => {
        return {
            email: element.patient_details.Patients_email
        }
    })
    // console.log(emailArr);
    emailArr.forEach(element1 => {
        const mailOptions = {
            from: "sahasouvik0054@gmail.com",
            to: element1.email,
            subject: "Reminder to take medicine on time.",
            text: "Hello I am robot who knows how to take care of his master , I am sure you forgot to take your medicine on your time.Please take your medicine on the prescribed time.Thank you. "
        };
        let transporter = nodemailer.createTransport({
            host: "smtp.gmail.com",
            service: "gmail",
            auth: {
                user: "sahasouvik0054@gmail.com",
                pass: "ffpvbnpukudnagbo"
            }
        });
        transporter.sendMail(mailOptions, (error, info) => {

            if (error) {
                console.log(error);
            }
            else {
                console.log("Email Sent succesfully." + info.response);
            }
        });
    })

})

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, "uploads");
    },
    filename: function (req, file, cb) {
        cb(null, file.originalname);
    },
});
var upload = multer({ storage: storage }).array('files');

app.post("/imageUpload", async (req, res) => {
    try {

        upload(req, res, (err) => {
            if (err) {
                console.log(err)
            }
            // console.log(req.files)
            let img = []

            req.files.forEach(file => {
                img.push(file.path)
            });
            res.json(img)
        })
    } catch (err) {
        console.log(err)
        res.status(500).send(err)
    }
})

app.post("/create", async (req, res) => {
    // console.log(req.body.masterData);
    // console.log(req.body.medicineDetails);
    // console.log(req.body.reportDetails);
    try {
        var docDetails = req.body.doctorDetails;
        var medDetails = req.body.medicineDetails;
        let now = new Date();
        let currentDate = date.format(now, 'DD-MM-YYYY');
        var medArr;
        var docArr;
        emailAddress = req.body.masterData.email;
        docArr = docDetails.map(element => {
            return {
                Doctors_Name: element.doctorsName,
                Doctors_Specialist: element.doctorSpecialist,
                Next_Checkup_Date: element.checkupDate,
            }
        })
        medArr = medDetails.map(element => {
            return {
                Medicine_Name: element.medicineName,
                Doctors_Name: element.doctorName,
                Medicine_Duration: element.duration,
                Medicine_Quantity: element.quantity,
                Medicine_Intake_Type: element.medicineType,
                Medicine_Intake_per_Day: element.frequency,
                Medicine_Intake_Time_per_Day: element.time1,
            }
        })
        medicineBuy = medArr;
        // console.log(reportArr)
        var insert = new healthcare_management({
            patient_details: {
                Patients_name: req.body.masterData.fullName,
                Patient_Phone_Number: req.body.masterData.phone,
                Infirmity_Occured: req.body.masterData.disease,
                Patients_email: req.body.masterData.email
            },
            Doctor_Details: docArr,
            Medicine_Details: medArr,
            // Report_Details: reportArr,
            CurrentDate: currentDate
        })
        var create = await insert.save()
        res.status(201).send(create)
    } catch (err) {
        console.log(err)
        res.status(500).send(err)
    }
})

app.post("/register", async (req, res) => {
    try {
        var insert = new user_details({
            name: req.body.name,
            email: req.body.email,
            phone_number: req.body.contact,
            password: req.body.password
        })
        var create = await insert.save()
        res.status(201).send(create)
    } catch (err) {
        console.log(err)
        res.status(500).send(err)
    }
})
app.post('/login', async (req, res) => {
    try {
        const userDetails = await user_details.find({ email: req.body.email });
        
        if (userDetails.length > 0) {
            if (userDetails[0].password === req.body.password) {
                res.status(200).json({
                    name: userDetails[0].name,
                    message: "Login successful",
                    code: "200"
                });
            } else {
                res.status(404).json({
                    details: [],
                    message: "Password Incorrect",
                    code: "404"
                });
            }
        } else {
            res.status(504).json({
                details: [],
                message: "Please enter correct Email Address",
                code: "504"
            });
        }
    } catch (err) {
        res.status(500).send(err);
    }
});


app.post("/PatientMobile", async (req, res) => {
    try {
        // console.log(req.body.masterData.PhoneNo)
        let masterData = [];
        var result = await healthcare_management.find();
        // console.log(result);
        result.forEach(element => {
            if (element.patient_details.Patient_Phone_Number == req.body.masterData.PhoneNo)
                masterData.push(element);
            // console.log(element.patient_details.Patient_Phone_Number);
        })
        res.status(200).json(masterData)

    } catch (err) {
        console.log(err)
        res.status(400).json(err)
    }
})

app.get("/readData/:id", async (req, res) => {
    try {
        const objectId = req.params.id
        let medicineDetails;
        let fullPatientDetails;
        let now = new Date();
        let currentDate = date.format(now, 'DD-MM-YYYY');
        //  console.log(mobile);
        var patientDetails = await healthcare_management.find({ _id: objectId });
        patientDetails.forEach(element => {
            medicineDetails = element.Medicine_Details;
            id = element._id;
        })
        // console.log(patientDetails)
        patientDetails.forEach(element => {
            fullPatientDetails = element;
        })
        res.status(200).json({
            ObjectId: id,
            masterData: medicineDetails,
            currentDate: currentDate,
            fullDetails: fullPatientDetails
        })
    } catch (error) {
        console.log(error)
        res.status(400).json(error)

    }
})

app.post("/fetchMedicine", async (req, res) => {
    try {
        var medicineRequestObject;
        var medicineRequestArr = [];
        var medicineObj = req.body.originalData.ObjectId;
        var timeLog = await healthcare_management.findById({ _id: medicineObj });
        // console.log(timeLog)
        var medicinerequest = req.body.originalData.masterData;
        medicinerequest.forEach(element => {
            timeLog.Medicine_Details.forEach(element1 => {
                if (element._id == element1._id) {
                    // console.log(element1._id, element._id)
                    medicineRequestObject = {
                        Medicine_Name: element.Medicine_Name,
                        Medicine_Duration: element.Medicine_Duration - element.Medicine_Intake_per_Day,
                        Medicine_Quantity: element.Medicine_Quantity - element.Medicine_Intake_per_Day,
                        Medicine_Intake_Time_per_Day: element.Medicine_Intake_Time_per_Day,
                        Medicine_Intake_per_Day: element.Medicine_Intake_per_Day,
                        Doctors_Name: element.Doctors_Name,
                        Medicine_Intake_Type: element.Medicine_Intake_Type
                    }
                }
            })
            medicineRequestArr.push(medicineRequestObject)
        })

        var finalLog = await healthcare_management.updateOne({ _id: medicineObj },
            {
                $set: {
                    Medicine_Details: medicineRequestArr
                }

            })
        console.log(finalLog)
        if (finalLog.nModified == 1) {
            res.status(200).json({
                message: "Medicine Taken Succesfully."
            })
        }
        else {
            res.status(406).json({
                message: "Error in Database Update . Please try again later ."
            })
        }
    } catch (error) {
        res.status(406).json({
            message: "Control Moves to catch block . Please try again after sometime ."
        })
    }
    // console.log(req.body);

})

app.delete("/delete/:id", async (req, res) => {
    try {
        var patientId = req.params.id;
        // console.log(patientId)
        const patientHistory = await healthcare_management.deleteOne({ _id: patientId })
        // console.log(patientHistory)
        const remainingPatient = await healthcare_management.find();
        if (patientHistory.deletedCount == 1) {
            res.status(200).json({
                message: "Patient History Deleted Succesfully.",
                data: remainingPatient
            })
        }
        else {
            res.status(400).json({
                message: "Patient History Not Deleted . Error may occur in Database ."
            })
        }
    } catch (error) {
        res.status(400).json({
            message: "Control moves to catch block . Please try again after sometime."
        })
    }

})
app.post("/update/:id", async (req, res) => {
    try {
        // console.log(req.body.reportDetails);
        var reportDetail = req.body.reportDetails;
        var reportArr;
        const objectId = req.params.id
        reportArr = reportDetail.map(element => {
            // console.log(imagePath)
            return {
                Report_Name: element.reportName,
                Report_Date: element.reportDate,
                Report_Details: element.reportDetails,
                Report_Upload: element.imageUpload
            }
        })
        var patientDetails = await healthcare_management.updateOne({ _id: objectId }, {
            $push: {
                "Report_Details": reportArr
            }
        });
        if (patientDetails.nModified == 1) {
            res.status(200).json({
                message: "Medicine Updated Succesfully."
            })
        }
        else {
            res.status(406).json({
                message: "Error in Updatation . Please try again later ."
            })
        }

    } catch (error) {
        console.log(error)
        res.status(400).json(error)
    }
})
app.listen(port, () => {
    console.log(`Connection succesfull on port ${port}`)
})