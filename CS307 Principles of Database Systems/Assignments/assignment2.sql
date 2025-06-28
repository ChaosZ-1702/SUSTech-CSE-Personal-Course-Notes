with target as (select station_id, count(distinct line_id) as line_count
      from line_detail
      group by station_id
      having count(distinct line_id) = 3)
select s.station_id, s.english_name, ld1.line_id, ld2.line_id from stations s
join line_detail ld1 on s.station_id = ld1.station_id
join line_detail ld2 on s.station_id = ld2.station_id
where ld1 < ld2 and s.station_id in (select station_id from target);

select line_id, cnt from (
    select *, avg(cnt) over() as avg from (
        select distinct line_id, count(station_id) over(partition by line_id) as cnt
        from line_detail
    ) sub2
) sub
where cnt > avg
order by line_id;

select line_id, district, cnt from (
      select distinct *, max(cnt) over(partition by district) as max from (
          select line_id, district, count(ld.station_id) over(partition by line_id, district) as cnt
          from line_detail ld
                   join stations on ld.station_id = stations.station_id
          where district != ''
      ) sub2
) sub
where cnt = max
order by district, line_id;

select bus_line, cnt from (
    select *, rank() over (order by cnt desc) as rnk from (
        select distinct bus_line, count(station_id) over(partition by bus_line) as cnt
        from bus_lines
    ) sub2
) sub
where rnk <= 3
order by bus_line;

select rank() over(order by cnt) as rnk, *, round(100.0 * cnt / lag(cnt, 1) over(), 1) || '%' as rate from (
    select district, count(distinct ld.station_id) as cnt
    from line_detail ld
    join stations on ld.station_id = stations.station_id
    where district != ''
    group by district
) sub;