# PeacockTeam
С помощью Java:
а) Считать файл (https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-4.txt.gz), состоящий из строк вида 

A1;B1;C1

A2;B2;C2

A3;B3

...

в строке может быть неограниченное число элементов

б) Найти множество уникальных строчек и разбить его на непересекающиеся группы по следующему критерию:

Если две строчки имеют совпадения непустых значений в одной или более колонках, они принадлежат одной группе. 

Например, строчки

111;123;222

200;123;100

300;;100

все принадлежат одной группе, так как первые две строчки имеют одинаковое значение 123 во второй колонке, а две последние одинаковое значение 100 в третьей колонке

строки

100;200;300

200;300;100

не должны быть в одной группе, так как значение 200 находится в разных колонках

в) Вывести полученные группы в файл в следующем формате:

Группа 1

строчка1

строчка2

строчка3

...

Группа 2 

строчка1

строчка2

строчка3

В начале вывода указать получившиееся число групп с более чем одним элементом.

Сверху расположить группы с наибольшим числом элементов.

Приемлимое время работы - до 30 секунд.

После выполнения задания необходимо отправить количество полученных групп с более чем одним элементом и время выполнения программы (мы не проверяем код если ответ неверный).

* Строки вида
 "8383"200000741652251"
 "79855053897"83100000580443402";"200000133000191"
 являются некорректными и должны пропускаться

** Если в группе две одинаковых строки - нужно оставить одну

Проект должен собираться с помощью maven или gradle в исполняемый jar.
В результате должно работать 
java -jar {название проекта}.jar тестовый-файл.txt

Правильно написанный алгоритм не должен потреблять много памяти (запускать с ограничением по памяти 1Гб (-Xmx1G))
