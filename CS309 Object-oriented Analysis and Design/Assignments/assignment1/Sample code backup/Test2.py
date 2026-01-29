def calculate_grade(scores):
    """根据分数计算等级"""
    average = sum(scores) / len(scores)
    
    if average >= 90:
        return "A"
    elif average >= 80:
        return "B"
    elif average >= 70:
        return "C"
    elif average >= 60:
        return "D"
    else:
        return "F"

def process_students(students_data):
    """处理学生数据并生成成绩报告"""
    report = []
    
    for student in students_data:
        name = student["name"]
        scores = student["scores"]
        
        grade = calculate_grade(scores)
        
        student_report = {
            "name": name,
            "average": sum(scores) / len(scores),
            "grade": grade,
            "status": "Pass" if grade != "F" else "Fail"
        }
        
        report.append(student_report)
    
    return report

def display_report(report):
    """显示成绩报告"""
    print("=== 学生成绩报告 ===")
    print(f"{'姓名':<10} {'平均分':<8} {'等级':<6} {'状态':<6}")
    print("-" * 35)
    
    for student in report:
        print(f"{student['name']:<10} {student['average']:<8.1f} {student['grade']:<6} {student['status']:<6}")

# 示例数据
students = [
    {"name": "张三", "scores": [85, 92, 78, 88]},
    {"name": "李四", "scores": [65, 72, 58, 61]},
    {"name": "王五", "scores": [95, 98, 92, 96]},
    {"name": "赵六", "scores": [45, 52, 38, 41]}
]

# 主程序
if __name__ == "__main__":
    # 处理学生数据
    grade_report = process_students(students)
    
    # 显示报告
    display_report(grade_report)
    
    # 统计通过人数
    passed_count = sum(1 for student in grade_report if student["status"] == "Pass")
    print(f"\n通过人数: {passed_count}/{len(students)}")