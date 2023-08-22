const mongoose = require('mongoose')

const healthSystem = new mongoose.Schema({
    patient_details: {
        Patients_name: {
            type: String,
            required: true,
        },
        Patient_Phone_Number:{
            type:String,
            required:true,
            unique:false
        },
        Infirmity_Occured: {
            type: String,
            required: true,
        },
        Patients_email:{
            type:String,
            required:true
        }
    },
    Doctor_Details: [{
        Doctors_Name: {
            type: String,
            required: true,
        },
        Doctors_Specialist: {
            type: String,
            required: true,
        },
        Next_Checkup_Date: {
            type: String,
            required: true
        }
    }],
    Medicine_Details: [{
        Medicine_Name: {
            type: String,
            required: true,
        },
        Doctors_Name: {
            type: String,
            required: true,
        },
        Medicine_Duration: {
            type: String,
            required: true,
        },
        Medicine_Quantity:{
            type:Number,
            require:true
        },
        Medicine_Intake_Type: {
            type: String,
            required: true,
        },
        Medicine_Intake_per_Day: {
            type: Number,
            required: true,
        },
        Medicine_Intake_Time_per_Day: {
            type: Object,
            required: true
        }
    }],
    Report_Details: [{
        Report_Name: {
            type: String,
            required: true,
        },
        Report_Date: {
            type: String,
            required: true,
        },
        Report_Details: {
            type: String,
            deafult:null
            // required: true,
        },
        Report_Upload:[{
            type:String,
            default:null
            // require:true
        }]
    }],
    CurrentDate:{
        type:String,
        require:true
    }
}, {
    timestamps: true
})

//COLLECTION

const HealthManagement = new mongoose.model('healthcare_management', healthSystem)

module.exports = HealthManagement
