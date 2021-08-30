const employeesList = document.querySelector('.employees');
const addEmployeeForm = document.querySelector(`.add-employee-form`);
const idValue = document.getElementById('employee_id');
const nameValue = document.getElementById('employee_name');
const salaryValue = document.getElementById('employee_salary');
const btnUpdate = document.querySelector('.btn-update');
let output = '<thead>\n' +
    '        <tr>\n' +
    '            <th>ID</th>\n' +
    '            <th>Name</th>\n' +
    '            <th>Salary</th>\n' +
    '            <th></th>\n' +
    '        </tr>\n' +
    '        </thead>';

const renderEmployees = (employees)  =>{
    employees.forEach(employee => {
        output += `<tr class="card-body">
            <td class="employee_id">${employee.id}</td>
            <td class="employee_name">${employee.name}</td>
            <td class="employee_salary">${employee.salary}</td>
            <td> 
                <a href="#" class="" id="edit-employee">Edit</a>
<!--                <span> | </span>-->
<!--                <a href="#" class="" id="delete-employee">Delete</a>-->
            </td>
        </tr>`;
    })
    employeesList.innerHTML = output;
}

const url = "http://localhost:8080/CWS_Practical_war/api/employees";

fetch(url)
    .then(res => res.json())
    .then(data => renderEmployees(data))

employeesList.addEventListener('click', (e) => {
    e.preventDefault();
    // let delButtonIsPressed = e.target.id == 'delete-employee';
    let editButtonIsPressed = e.target.id == 'edit-employee';

    let id = e.target.parentElement.parentElement.querySelector('.employee_id').textContent;

    // if(delButtonIsPressed){
    //     fetch(`${url}/${id}`, {
    //         method: 'DELETE',
    //     })
    //         .then(res => res.json())
    //         .then(() => location.reload())
    // }

    if(editButtonIsPressed){
        const parent = e.target.parentElement.parentElement;
        let idContent = parent.querySelector('.employee_id').textContent;
        idValue.value = idContent;
        let nameContent = parent.querySelector('.employee_name').textContent;
        nameValue.value = nameContent;
        let salaryContent = parent.querySelector('.employee_salary').textContent;
        salaryValue.value = salaryContent;
    }

    btnUpdate.addEventListener('click', () => {
        fetch(`${url}/`, {
            method: 'PUT',
            headers: {
                'Content-Type' : 'application/json'
            },
            body: JSON.stringify({
                name: nameValue.value,
                salary: salaryValue.value,
                id: idValue.value
            })
        })
            .then(res => res.json())
            .then(() => location.reload())
    })
});

addEmployeeForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: nameValue.value,
            salary: salaryValue.value
        })
    })
        .then(res => res.json())
        .then(data => {
            const dataArr = [];
            dataArr.push(data);
            renderEmployees(dataArr);
        })
        .then(() => location.reload())
})