const tasksList = document.querySelector('.tasks-list');
const addTaskForm = document.querySelector('.add-task-form');
const taskValue = document.getElementById('task-value');
const btnUpdate = document.querySelector('.update-btn');
let output = '';

const renderTasks = (tasks) => {
    tasks.forEach(task => {
        output += `    
    <div class="card" style="width: 18rem;">
        <div class="card-body" data-id=${task.taskId}>
            <h5 class="card-title" id="task-title">${task.taskName}</h5>
            <a href="#" class="card-link btn btn-light btn-sm" id="edit-task">Edit</a>
            <a href="#" class="card-link btn btn-light btn-sm" id="delete-task">Delete</a>
        </div>
    </div>
    `;
    });
    tasksList.innerHTML = output;
}

fetch('http://localhost:8080/task/readAll')
    .then(res => res.json())
    .then(data => renderTasks(data))

tasksList.addEventListener('click', (e) => {
    e.preventDefault();
    let delButtonPressed = e.target.id == 'delete-task';
    let editButtonPressed = e.target.id == 'edit-task';
    let id = e.target.parentElement.dataset.id;


    // Delete task
    if (delButtonPressed) {
        fetch(`http://localhost:8080/task/delete/${id}`,
            {
                method: 'DELETE',
            })
            .then(res => res.json())
            .then(() => location.reload())
    }

    if (editButtonPressed) {
        const parent = e.target.parentElement;
        let taskContent = parent.querySelector('.card-title').textContent;

        taskValue.value = taskContent;
    }

    // Update task
    btnUpdate.addEventListener('click', (e) => {
        e.preventDefault;
        fetch(`http://localhost:8080/task/update/${id}`,
            {
                method: 'PUT',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify({
                    taskName: taskValue.value
                })
            })
            .then(res => res.json())
            .then(() => location.reload())
    })
})
// Create new task
addTaskForm.addEventListener('submit', (e) => {
    e.preventDefault;

    fetch('http://localhost:8080/task/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            taskName: taskValue.value
        })
    })
        .then(res => res.json())
        .then(data => {
            const dataArr = [];
            dataArr.push(data);
            renderTasks(dataArr);
        })

    taskValue.value = '';
})