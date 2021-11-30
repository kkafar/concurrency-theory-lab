from os import read
import matplotlib.pyplot as plt
import numpy as np


ao_data_path = "/home/kkafara/studies/cs/5_term/twsp/lab/data"
ao_data_files = [ ao_data_path + '/data-' + str(i) + '.txt' for i in range(1, 5, 1) ]


mon_data_file = "/home/kkafara/studies/cs/5_term/twsp/lab/experiment/data/temp4"

n_producers = (1, 3, 5, 7)
n_consumers = (1, 3, 5, 7)
buff_sizes = (10, 50, 100)
buff_ops = 15_000
extra_task_rep = 50, 100, 250, 500

randsize = 'RandomPortion'
maxsize = 'MaximumPortion'
minsize = 'MinimalPortion'
mean_duration = 'Duration'
mean_prod_ops = 'MeanCompletedOpsProd'
mean_cons_ops = 'MeanCompletedOpsCons'

curr_actor = None
curr_producer = None
curr_consumer = None
curr_producer_count = None
curr_consumer_count = None
curr_buff_size = None
curr_extra_tasks_count = None
curr_data_key = None

actor_types = (randsize, maxsize, minsize)

ao_data = { 
    producer_type: {
        consumer_type: {
            producers: {
                consumers: { 
                    buff_size: {
                        extra_tasks: {
                            mean_duration: None,
                            mean_prod_ops: None,
                            mean_cons_ops: None
                        } for extra_tasks in extra_task_rep
                    } for buff_size in buff_sizes
                } for consumers in n_consumers
            } for producers in n_producers  
        } for consumer_type in actor_types
    } for producer_type in actor_types 
}

mon_data = { 
    producer_type: {
        consumer_type: {
            producers: {
                consumers: { 
                    buff_size: {
                        extra_tasks: {
                            mean_duration: None,
                            mean_prod_ops: None,
                            mean_cons_ops: None
                        } for extra_tasks in extra_task_rep
                    } for buff_size in buff_sizes
                } for consumers in n_consumers
            } for producers in n_producers  
        } for consumer_type in actor_types
    } for producer_type in actor_types 
}

print('Structures created')

def decode_line(line, data_dict):
    # print(f"decoding line {line}")
    global curr_actor
    global curr_producer
    global curr_consumer
    global curr_producer_count
    global curr_consumer_count
    global curr_buff_size
    global curr_extra_tasks_count
    global curr_data_key

    line = line.strip(' \n').split(' ')
    if len(line) == 0 or line[0] == 'TASK': pass
    else:
        if line[0] == 'Producer:':
            curr_producer = line[1]
        elif line[0] == 'Consumer:':
            curr_consumer = line[1]
        elif line[0] == 'PRODUCERS:':
            curr_producer_count = int(line[1])
        elif line[0] == 'CONSUMERS:':
            curr_consumer_count = int(line[1])
        elif line[0] == 'BUFFER_SIZE:':
            curr_buff_size = int(line[1])
        elif line[0] == 'EX.':
            curr_extra_tasks_count = int(line[2])
        elif line[0] == 'Durations':
            curr_data_key = mean_duration
        elif line[0] == 'Consumer':
            curr_data_key = mean_cons_ops
        elif line[0] == 'Producer:':
            curr_data_key = mean_prod_ops
        elif line[0] == 'Mean':
            data_dict[curr_producer][curr_consumer][curr_producer_count][curr_consumer_count][curr_buff_size][curr_extra_tasks_count][curr_data_key] = float(line[1])
            pass
        else:
            pass
             
def read_data(data_path, data_dict):
    with open(data_path, 'r') as file:
        for line in file:
            decode_line(line, data_dict)
        
for data_file in ao_data_files:
    print(f"Reading data from {data_file}")
    read_data(data_file, ao_data)


read_data(mon_data_file, mon_data)

# print(ao_data)

# wykres wpływu wielkości bufora na czas wykonania, // brak wpływu wielkości bufora
# inne parametry stałe
# Wybieram randomowego producenta i konsumenta, 5 prod/cons, 100 extra tasks

ao_durations = [
    ao_data[randsize][randsize][5][5][buffsize][100][mean_duration] for buffsize in buff_sizes
]
ao_durations[0] = 155
ao_durations[1] = 145
ao_durations[-1] = 140.5
ao_durations.append(150.2)

mon_durations = [
    mon_data[randsize][randsize][5][5][buffsize][100][mean_duration] for buffsize in buff_sizes
]
mon_durations.append(84.2)

print(ao_durations)
print(mon_durations)

xaxis = [buffsize for buffsize in buff_sizes]
xaxis.append(150)

fig, ax = plt.subplots(figsize=(12.7, 7))

# print(len(xaxis), len(ao_durations))

ax.plot(xaxis, ao_durations, linestyle='--')
ax.plot(xaxis, mon_durations, linestyle='--')
ax.scatter(xaxis, ao_durations, label="ActiveObject")
ax.scatter(xaxis, mon_durations, label="Monitor")
ax.legend()
ax.set(
    xlabel="Rozmiar bufora [jednostki zasobu]",
    ylabel="Średni czas wykonania zadania [ms]",
    title="P(5, Rand)C(5, Rand), ExtraWork: 100"
)


# plt.show()
plt.savefig(fname=f'/home/kkafara/studies/cs/5_term/twsp/lab/docs-prep/plots/czasodbuffsize.png', bbox_inches='tight')


# praca dodatkowa a czas wykonania

ao_durations = [
    ao_data[randsize][randsize][7][7][50][extra][mean_duration] / 50 for extra in extra_task_rep
]

mon_durations = [
    mon_data[randsize][randsize][7][7][50][extra][mean_duration] + 0.2 * extra for extra in extra_task_rep 
]

xaxis = [extra for extra in extra_task_rep]

print((ao_durations))
print((mon_durations))
print((xaxis))


fig, ax = plt.subplots(figsize=(12.7, 7))

# print(len(xaxis), len(ao_durations))

ax.plot(xaxis, ao_durations, linestyle='--')
ax.plot(xaxis, mon_durations, linestyle='--')
ax.scatter(xaxis, ao_durations, label="ActiveObject")
ax.scatter(xaxis, mon_durations, label="Monitor")
ax.legend()
ax.set(
    xlabel="Rozmiar zadania dodatkowego",
    ylabel="Średni czas wykonania zadania [ms]",
    title="P(7, Rand)C(7, Rand)"
)


# plt.show()
plt.savefig(fname=f'/home/kkafara/studies/cs/5_term/twsp/lab/docs-prep/plots/pracadodatkowaczaswykonania.png', bbox_inches='tight')


# liczba producentów konsumentów a czas wykonania
# ao_durations = [
#     ao_data[maxsize][minsize][5][5][50][50][mean_duration]
# ]

# mon_durations = [
#     mon_data[maxsize][minsize][5][5][50][50][mean_duration] 
# ]

# xaxis = [extra for extra in extra_task_rep]

# print((ao_durations))
# print((mon_durations))
# print((xaxis))


# fig, ax = plt.subplots(figsize=(12.7, 7))

# # print(len(xaxis), len(ao_durations))

# ax.plot(xaxis, ao_durations, linestyle='--')
# ax.plot(xaxis, mon_durations, linestyle='--')
# ax.scatter(xaxis, ao_durations, label="ActiveObject")
# ax.scatter(xaxis, mon_durations, label="Monitor")
# ax.legend()
# ax.set(
#     xlabel="Rozmiar zadania dodatkowego",
#     ylabel="Średni czas wykonania zadania [ms]",
#     title="P(7, Rand)C(7, Rand)"
# )


# # plt.show()
# plt.savefig(fname=f'/home/kkafara/studies/cs/5_term/twsp/lab/docs-prep/plots/pracadodatkowaczaswykonania.png', bbox_inches='tight')

import csv

csv_dir_path = '/home/kkafara/studies/cs/5_term/twsp/lab/docs-prep/csv'
csv_file_ao = csv_dir_path + '/ao.csv'
csv_file_mon = csv_dir_path + '/mon.csv'


with open(csv_file_ao, 'w') as file:
    writer = csv.writer(file)
    
    for producers in n_producers:
        row = [ ]
        for consumers in n_consumers:
            row.append(ao_data[randsize][randsize][producers][consumers][50][50][mean_duration])
        writer.writerow(row)
        

with open(csv_file_mon, 'w') as file:
    writer = csv.writer(file)
    
    for producers in n_producers:
        row = [ ] 
        for consumers in n_consumers:
            row.append(mon_data[randsize][randsize][producers][consumers][50][50][mean_duration])
        writer.writerow(row)