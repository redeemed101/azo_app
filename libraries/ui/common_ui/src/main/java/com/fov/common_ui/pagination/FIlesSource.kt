package com.fov.common_ui.pagination

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fov.domain.utils.constants.QueryConstants
import java.io.File

class FilesSource constructor(
    private val directory : File,
    private val fileExtension : String
) : PagingSource<Int, File>() {
    override fun getRefreshKey(state: PagingState<Int, File>): Int? {
        return state.anchorPosition?.let {
        state.closestPageToPosition(it)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, File> {
        return try {
            val nextPage = params.key ?: 1
            var files = mutableListOf<File>()
            directory.walk().forEach {
                if(it.isDirectory) {
                    val f = it.listFiles { file ->
                        file.name.contains(fileExtension) && file.length() > 0
                    }
                    f.take(QueryConstants.NUM_ROWS).drop((nextPage - 1) * QueryConstants.NUM_ROWS)
                    files.addAll(f)
                }
                else{
                    if(it.endsWith(fileExtension) && it.length() > 0){
                        files.add(it)
                    }
                }

            }
            LoadResult.Page(
                data = files,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = nextPage.plus(1)
            )
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}