import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report
import joblib

df = pd.read_csv("dummy_students_dataset_with5scores.csv")

X = df[["attendance", "avg_score", "min_score", "feePaid", "attempts"]]
y = df["risk"]

X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42
)

scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

model = LogisticRegression()
model.fit(X_train_scaled, y_train)

y_pred = model.predict(X_test_scaled)
print("Model Performance Report:")
print(classification_report(y_test, y_pred))

joblib.dump(model, "dropout_model.pkl")
joblib.dump(scaler, "scaler.pkl")
print("✅ Model and scaler saved successfully!")

def predict_dropout(student_data):
    avg_score = sum(student_data["scores"]) / len(student_data["scores"])
    min_score = min(student_data["scores"])

    features = pd.DataFrame([{
        "attendance": student_data["attendance"],
        "avg_score": avg_score,
        "min_score": min_score,
        "feePaid": int(student_data["feePaid"]),
        "attempts": student_data["attempts"]
    }])

    features_scaled = scaler.transform(features)

    prob = model.predict_proba(features_scaled)[0][1]  
    risk_score = round(float(prob), 2)

    if risk_score < 0.4:
        risk_level = "Low"
    elif risk_score < 0.7:
        risk_level = "Medium"
    else:
        risk_level = "High"

    return {
        "studentId": student_data["studentId"],
        "riskLevel": risk_level,
        "riskScore": risk_score
    }

print("\nEnter details of the student to predict dropout risk:\n")

student_id = input("Student ID: ").strip()
attendance = float(input("Attendance percentage (e.g., 75): "))

scores = list(map(float,
                  input("Enter 5 test scores separated by spaces: ").split()))
while len(scores) != 5:
    print("Please enter exactly 5 numbers.")
    scores = list(map(float,
                      input("Enter 5 test scores separated by spaces: ").split()))

fee_input = input("Has the student paid the fees? (yes/no): ").strip().lower()
fee_paid = fee_input in ("yes", "y")

attempts = int(input("Number of exam attempts: "))

runtime_input = {
    "studentId": student_id,
    "attendance": attendance,
    "scores": scores,
    "feePaid": fee_paid,
    "attempts": attempts
}

result = predict_dropout(runtime_input)
print("\nPrediction Result:")
print(f"Student ID : {result['studentId']}")
print(f"Risk Level : {result['riskLevel']}")
print(f"Risk Score : {result['riskScore']}")
